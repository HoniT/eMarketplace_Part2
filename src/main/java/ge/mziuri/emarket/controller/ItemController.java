package ge.mziuri.emarket.controller;

import ge.mziuri.emarket.exception.ResourceNotFoundException;
import ge.mziuri.emarket.model.dto.ItemDto;
import ge.mziuri.emarket.model.entity.Item;
import ge.mziuri.emarket.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity<List<Item>> getItems(@RequestParam int pageNumber,
                                               @RequestParam int pageSize,
                                               @RequestParam String sortParam) {
        return ResponseEntity.ok(itemService.getItems(pageNumber, pageSize, sortParam));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(itemService.getItemById(id));
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Void> addItem(@Valid @ModelAttribute ItemDto itemDto) {
        itemService.addItem(itemDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable("id") Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/image/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable("fileName") String fileName) {
        try {
            Resource resource = itemService.loadImageAsResource(fileName);
            if (resource == null) {
                throw new ResourceNotFoundException("Requested image was not found");
            }
            String contentType = itemService.getContentType(fileName);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
