package et.store.api_demo.demo.presentation.controller;

import et.store.api_demo.demo.domain.entity.Store;
import et.store.api_demo.demo.domain.service.interfaces.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

  @Autowired
  private StoreService storeService;

  // Obtener todas las tiendas
  @GetMapping
  public ResponseEntity<List<Store>> getAllStores() {
    List<Store> stores = storeService.getAllStores();
    return ResponseEntity.ok().body(stores);
  }

  // Obtener una tienda por su ID
  @GetMapping("/{id}")
  public ResponseEntity<Store> getStoreById(@PathVariable Integer id) {
    Optional<Store> store = storeService.getStoreById(id);
    return store.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  // Crear una nueva tienda
  @PostMapping
  public ResponseEntity<Store> createStore(@RequestBody Store store) {
    Store createdStore = storeService.saveStore(store);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdStore);
  }

  // Actualizar una tienda existente
  @PutMapping("/{id}")
  public ResponseEntity<Store> updateStore(@PathVariable Integer id, @RequestBody Store store) {
    Store updatedStore = storeService.updateStore(id, store);
    return ResponseEntity.ok().body(updatedStore);
  }

  // Eliminar una tienda por su ID
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteStore(@PathVariable Integer id) {
    storeService.deleteStore(id);
    return ResponseEntity.noContent().build();
  }
}
