package et.store.api_demo.demo.domain.service.implement;

import et.store.api_demo.demo.data.repository.StoreRepository;
import et.store.api_demo.demo.domain.entity.Store;
import et.store.api_demo.demo.domain.service.interfaces.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreServiceImplement implements StoreService {

  @Autowired
  private StoreRepository storeRepository;

  @Override
  public List<Store> getAllStores() {
    return storeRepository.findAll();
  }

  @Override
  public Optional<Store> getStoreById(Integer id) {
    return storeRepository.findById(id);
  }

  @Override
  public Store saveStore(Store store) {
    return storeRepository.save(store);
  }

  @Override
  public void deleteStore(Integer id) {
    storeRepository.deleteById(id);
  }

  @Override
  public Store updateStore(Integer id, Store store) {
    // Obtener la tienda existente por su ID
    Store existingStore = getStoreById(id)
        .orElseThrow(() -> new RuntimeException("Store not found"));

    // Actualizar los campos de la tienda existente con los valores de la tienda recibida
    existingStore.setName(store.getName());
    existingStore.setCity(store.getCity());
    existingStore.setOpeningHours(store.getOpeningHours());
    // Actualizar otros campos según sea necesario

    // Guardar y retornar la tienda actualizada
    return storeRepository.save(existingStore);
  }

}
