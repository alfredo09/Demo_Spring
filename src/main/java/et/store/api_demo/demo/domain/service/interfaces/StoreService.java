package et.store.api_demo.demo.domain.service.interfaces;

import et.store.api_demo.demo.domain.entity.Store;

import java.util.List;
import java.util.Optional;

public interface StoreService {
  List<Store> getAllStores();
  Optional<Store> getStoreById(Integer id);
  Store saveStore(Store store);
  void deleteStore(Integer id);
  Store updateStore(Integer id, Store store);
}
