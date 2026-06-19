package ge.mziuri.emarket.service;

import ge.mziuri.emarket.exception.ResourceNotFoundException;
import ge.mziuri.emarket.mapper.ItemMapper;
import ge.mziuri.emarket.model.dto.ItemDto;
import ge.mziuri.emarket.model.dto.ItemResponseDto;
import ge.mziuri.emarket.model.entity.Item;
import ge.mziuri.emarket.model.entity.User;
import ge.mziuri.emarket.repository.ItemRepository;
import ge.mziuri.emarket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemMapper itemMapper;
    private final Path uploadPath;

    public ItemService(@Value("${spring.images.upload-dir:uploads}") String uploadDir) {
        this.uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    public List<ItemResponseDto> getItems(int pageNumber, int pageSize, String sortParam) {
        Sort sort;
        if (sortParam == null) sortParam = "dateDesc";

        sort = switch (sortParam) {
            case "dateAsc" -> Sort.by(Sort.Direction.ASC, "submissionTime");
            case "priceAsc" -> Sort.by(Sort.Direction.ASC, "price");
            case "priceDesc" -> Sort.by(Sort.Direction.DESC, "price");
            default -> Sort.by(Sort.Direction.DESC, "submissionTime");
        };

        PageRequest page = PageRequest.of(pageNumber, pageSize, sort);
        return itemRepository.findAll(page).getContent().stream().map(itemMapper::entityToResponseDto).toList();
    }

    public ItemResponseDto getItemById(Long id) {
        return itemMapper.entityToResponseDto(itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No item found with id: " + id)));
    }

    public void addItem(ItemDto itemDto) {
        String username = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();

        User user = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Item item = itemMapper.addDtoToEntity(itemDto);
        item.setUser(user);

        MultipartFile file = itemDto.getPhoto();
        List<String> allowedContentTypes = List.of("image/jpeg", "image/png", "image/webp", "image/gif");

        if (file != null && !file.isEmpty()) {

            if (allowedContentTypes.contains(file.getContentType())) {
                try {
                    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    Files.createDirectories(uploadPath);
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                    item.setPhotoUrl(fileName);
                } catch (IOException e) {
                    throw new RuntimeException("Could not store image file: " + e.getMessage(), e);
                }
            } else {
                throw new IllegalArgumentException("Invalid file format. Please upload a valid image.");
            }
        }

        itemRepository.save(item);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }


    public Resource loadImageAsResource(String fileName) {
        try {
            Path filePath = uploadPath.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            }
            return null;

        } catch (Exception e) {
            throw new RuntimeException("Error reading file: " + fileName, e);
        }
    }

    public String getContentType(String fileName) {
        try {
            Path filePath = uploadPath.resolve(fileName).normalize();
            String contentType = Files.probeContentType(filePath);

            return Objects.requireNonNullElse(contentType, "application/octet-stream");

        } catch (Exception e) {
            return "application/octet-stream";
        }
    }
}
