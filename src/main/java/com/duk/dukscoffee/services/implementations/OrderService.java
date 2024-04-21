package com.duk.dukscoffee.services.implementations;

import com.duk.dukscoffee.entities.*;
import com.duk.dukscoffee.exceptions.ClientNotFoundException;
import com.duk.dukscoffee.exceptions.OrderNotFoundException;
import com.duk.dukscoffee.exceptions.UserNotFoundException;
import com.duk.dukscoffee.http.DTO.OrderDTO;
import com.duk.dukscoffee.http.DTO.OrderDetailsDTO;
import com.duk.dukscoffee.http.DTO.OrderXProductDTO;
import com.duk.dukscoffee.http.DTO.OrderXProductDetailsDTO;
import com.duk.dukscoffee.http.DTO.ProductBillDTO;
import com.duk.dukscoffee.http.DTO.ProductDTO;
import com.duk.dukscoffee.http.DTO.UserDTO;
import com.duk.dukscoffee.respositories.BillRepository;
import com.duk.dukscoffee.respositories.ClientRepository;
import com.duk.dukscoffee.respositories.OrderRepository;
import com.duk.dukscoffee.respositories.OrderXProductRepository;
import com.duk.dukscoffee.respositories.ProductRepository;
import com.duk.dukscoffee.respositories.UserEntityRepository;
import com.duk.dukscoffee.services.interfaces.IOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class OrderService implements IOrderService  {

    public static final String IS_ALREADY_USE = "The %s is already use";
    public static final String IS_NOT_FOUND = "The %s is not found";
    public static final String IS_NOT_ALLOWED = "The %s is not allowed";
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderXProductRepository orderXProductRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private UserEntityRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;


    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) throws UserNotFoundException, ClientNotFoundException{

        UserEntity user = userRepository.findById(orderDTO.getUserId()).orElse(null);

        if(user == null){
            throw new UserNotFoundException(String.format(IS_NOT_FOUND, "users").toUpperCase());
        }

        Client client = clientRepository.findById(orderDTO.getClientId()).orElse(null);

        if (client == null) {
            throw new ClientNotFoundException(String.format(IS_NOT_FOUND, "client").toUpperCase());
        }
        
        client.setLastVisit(new Date());
        clientRepository.save(client);
        Order order = new Order();
        order.setDate(new Date());
        order.setClient(client);
        order.setUser(user);

        Bill bill = createBill(orderDTO.getOrderxproducts());
        order.setBill(bill);
        

        order = orderRepository.save(order);
        createOrderXProducts(orderDTO.getOrderxproducts(), order.getId());

        OrderDTO createdOrderDTO = new OrderDTO();
        BeanUtils.copyProperties(order, createdOrderDTO);
        return createdOrderDTO;
    }
    
    @Override
    public List<Order> getOrders(){
        return (List<Order>) orderRepository.findAll();

    }

    @Override
    public OrderDetailsDTO getOrderById(Integer orderId) throws OrderNotFoundException {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null){
            throw new OrderNotFoundException(String.format(IS_NOT_FOUND, "order").toUpperCase());
        }

        OrderDetailsDTO OrderResponseDTO = new OrderDetailsDTO();
        BeanUtils.copyProperties(order, OrderResponseDTO);
        return OrderResponseDTO;
    }

    public OrderXProductDetailsDTO getOrdersById(Integer orderId) throws OrderNotFoundException {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null){
            throw new OrderNotFoundException(String.format(IS_NOT_FOUND, "order").toUpperCase());
        }

        OrderXProductDetailsDTO orderResponseDTO = new OrderXProductDetailsDTO();

        orderResponseDTO.setProductList(order.getProductoOrdenList().stream().map(value -> {
            ProductBillDTO productDTO = new ProductBillDTO();
            BeanUtils.copyProperties(value.getProduct(), productDTO);
            productDTO.setAmountBill(value.getAmount());
            return productDTO;
        }).collect(Collectors.toList()));

        //bean util con user
        orderResponseDTO.setUser(new UserDTO());
        BeanUtils.copyProperties(order.getUser(), orderResponseDTO.getUser());

        orderResponseDTO.setId(order.getId());
        orderResponseDTO.setDate(order.getDate());
        orderResponseDTO.setClient(order.getClient());
        orderResponseDTO.setBill(order.getBill());
        return orderResponseDTO;
    }

    private Bill createBill(List<OrderXProductDTO> orderXProductDTOs) {
        
        Bill bill = new Bill();
        bill.setIva(true);
        double basePrice = 0.0;
        double totalPrice = 0.0;
        double totalDiscount = 0.0;
        double productTotalPrice =0.0;


        for (OrderXProductDTO orderXProductDTO : orderXProductDTOs) {
            Product product = productRepository.findById(orderXProductDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException((String.format(IS_NOT_FOUND, "product").toUpperCase())));
            if(product.getDiscount()> 0){
                double discount = product.getBasePrice() * product.getDiscount() / 100;
                totalDiscount += discount *orderXProductDTO.getAmount(); 
             productTotalPrice = (product.getBasePrice() - discount) * orderXProductDTO.getAmount();

            } else{
                productTotalPrice = product.getBasePrice() * orderXProductDTO.getAmount();
            }
            
            basePrice += productTotalPrice;
            totalPrice += productTotalPrice*1.19;

            // Updated Product Stock ... Stand By
        }

        bill.setDiscounts(totalDiscount);
        bill.setBasePrice(basePrice);
        bill.setTotalPrice(totalPrice);
        bill.setDateBill(new Date());

        return billRepository.save(bill);
    }

    private void createOrderXProducts(List<OrderXProductDTO> orderXProductDTOs, Integer orderId) {
        for (OrderXProductDTO orderXProductDTO : orderXProductDTOs) {
            Product product = productRepository.findById(orderXProductDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException((String.format(IS_NOT_FOUND, "Product").toUpperCase())));
            Order order = orderRepository.findById(orderId) 
                    .orElseThrow(() -> new RuntimeException((String.format(IS_NOT_FOUND, "order").toUpperCase())));
            OrderXProduct orderXProduct = new OrderXProduct();
            orderXProduct.setProduct(product);
            orderXProduct.setAmount(orderXProductDTO.getAmount());
            orderXProduct.setOrder(order); 
            orderXProductRepository.save(orderXProduct);
        }
    }
}
