package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    public final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, Book param) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(param.getPrice());
        findItem.setName(param.getName());
        findItem.setStockQuantity(param.getStockQuantity());
        /*
        * save, merge 등을 호출할 필요가 있을까? -> 아무것도 호출할 필요가 없다.
        * 왜냐하면, findOne으로 가져온 findItem이라는 변수는 영속 상태이다.
        * 이 findItem을 set하게 되면 @Transactional 어노테이션에 의해 트랜젝션이 commit이 된다.
        * 글면 JAP는 flush를 하게 되고 영속성 컨텍스트 중에 해당 엔티티를 변경하게 되고 그것을 update하게 된다.
        * 이것을 '변경 감지에 의한 데이터 변경'이라고 한다.
        *
        * 해당 방법이 아닌 다른 방법이 바로 병합(merge)를 사용하는 것이다.
        * 병합은 준영속 상태의 엔티티를 영속 상태로 변경할 때 사용하는 기능이다.
        * merge 방법은 해당 코드에서 사용 중이며 바로 위에 saveItem() 참고.
        *
        * merge는 속성들을 선택하여 변경할 수 없으며, 모든 속성이 변경된다. 만약 존재하는 속성임에도 변경하지 않으면 null 처리 된다.
        * 따라서, 엔티티를 변경할 때는 병합 방법은 사용되지 않으며, 변경 감지 방법을 사용한다.
        * */
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

}
