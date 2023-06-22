package com.phmth.laptopshop.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phmth.laptopshop.dto.CartItem;
import com.phmth.laptopshop.dto.FormOrderInfo;
import com.phmth.laptopshop.dto.FormSearchOrder;
import com.phmth.laptopshop.entity.OrderDetailEntity;
import com.phmth.laptopshop.entity.OrderEntity;
import com.phmth.laptopshop.entity.UserEntity;
import com.phmth.laptopshop.enums.StateCheckout;
import com.phmth.laptopshop.enums.StateOrder;
import com.phmth.laptopshop.exception.OrderException;
import com.phmth.laptopshop.repository.IOrderDetailRepository;
import com.phmth.laptopshop.repository.IOrderRepository;
import com.phmth.laptopshop.repository.IProductRepository;
import com.phmth.laptopshop.repository.IUserRepository;
import com.phmth.laptopshop.service.IOrderService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
@Transactional
public class OrderService implements IOrderService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IProductRepository productRepository;

	@Autowired
	private IOrderRepository orderRepository;

	@Autowired
	private IOrderDetailRepository orderDetailRepository;

	@Override
	public Page<OrderEntity> findAll(int page, int limit) {
		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		Pageable pageable = PageRequest.of(page - 1, limit, sort);
		return orderRepository.findAll(pageable);
	}

	@Override
	public Page<OrderEntity> findAll(int page, int limit, FormSearchOrder formSearchOrder) {
		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		Pageable pageable = PageRequest.of(page - 1, limit, sort);
		Specification<OrderEntity> specification = new Specification<OrderEntity>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<OrderEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (!formSearchOrder.getNameOrder().isBlank()) {
					predicates.add(criteriaBuilder.like(root.get("name"), "%" + formSearchOrder.getNameOrder() + "%"));
				}
				if (!formSearchOrder.getPayment().isBlank()) {
					predicates.add(criteriaBuilder.equal(root.get("payment"), formSearchOrder.getPayment()));
				}
				if (formSearchOrder.getStatus() != null) {
					predicates.add(criteriaBuilder.equal(root.get("stateOrder"), formSearchOrder.getStatus()));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}

		};
		return orderRepository.findAll(specification, pageable);
	}

	@Override
	public List<OrderEntity> findByUser(long id) {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(id);
		return orderRepository.findByUser(userEntity);
	}

	@Override
	public Optional<OrderEntity> findOne(long id) {
		return orderRepository.findById(id);
	}

	@Override
	public OrderEntity Order(Collection<CartItem> carts, FormOrderInfo formOrderInfo) {
		// If the input is null, throw exception
		if (formOrderInfo == null || carts == null) {
			throw new OrderException("The input is null!");
		}

		// If the input is empty, throw exception
		if (formOrderInfo.isEmpty() || carts.isEmpty()) {
			throw new OrderException("The input is empty!");
		}

		// If user is not found, throw exception
		Optional<UserEntity> userEntity = userRepository.findById(formOrderInfo.getUserId());
		if (userEntity.isEmpty()) {
			return null;
		}

		// Save order information
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setUser(userEntity.get());
		orderEntity.setName(userEntity.get().getFullname());
		orderEntity.setEmail(userEntity.get().getEmail());
		orderEntity.setPhone(formOrderInfo.getPhone());
		orderEntity.setAddress_delivery(formOrderInfo.getAddress_delivery());
		orderEntity.setCreated_at(new Date());
		orderEntity.setStateCheckout(StateCheckout.UNPAID);
		orderEntity.setStateOrder(StateOrder.PENDING);
		orderEntity.setNum(formOrderInfo.getNum());
		orderEntity.setTotal_money(formOrderInfo.getTotalMoney());
		orderEntity.setPayment(formOrderInfo.getPayment());
		OrderEntity order = orderRepository.save(orderEntity);

		if (order == null) {
			return null;
		}

		// Save order details
		for (CartItem cartItem : carts) {
			OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
			orderDetailEntity.setOrder(orderRepository.findById(order.getId()).get());
			orderDetailEntity.setProduct(productRepository.findById(cartItem.getProductId()).get());
			orderDetailEntity.setPrice(cartItem.getPrice());
			orderDetailEntity.setNum(cartItem.getNumProduct());
			orderDetailEntity.setTotalPrice(cartItem.getTotalPrice());
			orderDetailEntity.setDiscount(cartItem.getDiscount());
			orderDetailRepository.save(orderDetailEntity);
		}

		// Save order code. vd: (orderId + userId + orderDate) = codeOrder
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = formatter.format(order.getCreated_at());
		String codeOrder = "" + order.getId() + order.getUser().getId() + date;
		order.setCodeOrder(codeOrder);

		return orderRepository.save(order);
	}

	@Override
	public boolean updateStateOrder(long id, StateOrder status) {

		// If the data to be modified is not found, throw exception
		Optional<OrderEntity> orderEntity = orderRepository.findById(id);
		if (orderEntity.isEmpty()) {
			throw new OrderException("The data to be modified is not found!");
		}

		// If saving modification fail, return false
		orderEntity.get().setStateOrder(status);
		if (orderRepository.save(orderEntity.get()) == null) {
			return false;
		}

		return true;
	}

	@Override
	public void updateStateCheckout(long id, StateCheckout status) {
		// If the data to be modified is not found, throw exception
		Optional<OrderEntity> orderEntity = orderRepository.findById(id);
		if (orderEntity.isEmpty()) {
			throw new OrderException("The data to be modified is not found!");
		}

		// If saving modification fail, return false
		orderEntity.get().setStateCheckout(status);
		orderRepository.save(orderEntity.get());

	}

	@Override
	public boolean cancelOrder(long id) {

		// If the data to be modified is not found, throw exception
		Optional<OrderEntity> orderEntity = orderRepository.findById(id);
		if (orderEntity.isEmpty()) {
			throw new OrderException("The data to be modified is not found!");
		}

		// If saving modification fail, return false
		orderEntity.get().setStateOrder(StateOrder.CANCELLED);
		if (orderRepository.save(orderEntity.get()) == null) {
			return false;
		}

		return true;
	}

	@Override
	public Optional<OrderEntity> findByCodeOrder(String codeOrder) {
		return orderRepository.findByCodeOrder(codeOrder);
	}

}
