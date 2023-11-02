package com.example.rschir6.services;

import com.example.rschir6.models.*;
import com.example.rschir6.repositories.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BasketService {

    private final BookRepository bookRepository;

    private final TelephoneRepository telephoneRepository;

    private final WashingMachineRepository washingMachineRepository;

    private final BasketRepository basketRepository;

    private final ClientRepository clientRepository;

    private final BasketDetailsRepository basketDetailsRepository;

    public BasketService(BookRepository bookRepository, TelephoneRepository telephoneRepository, WashingMachineRepository washingMachineRepository, BasketRepository basketRepository, ClientRepository clientRepository, BasketDetailsRepository basketDetailsRepository) {
        this.bookRepository = bookRepository;
        this.telephoneRepository = telephoneRepository;
        this.washingMachineRepository = washingMachineRepository;
        this.basketRepository = basketRepository;
        this.clientRepository = clientRepository;
        this.basketDetailsRepository = basketDetailsRepository;
    }

    public Iterable<Basket> getAll() {
        return basketRepository.findAll();
    }

    public String update(int id, Basket newBasket){
        Optional<Basket> optionalBasket = basketRepository.findById(id);
        if (optionalBasket.isEmpty()) {
            return "Basket not found";
        }
        newBasket.setId(id);
        basketRepository.save(newBasket);
        return "Update data";
    }

    public String deleteByClientId(int id){
        Optional<Basket> optionalBasket = basketRepository.findBasketByClient_Id(id);
        if (optionalBasket.isEmpty()) {
            return "Basket not found";
        }
        basketRepository.delete(optionalBasket.get());
        return "Data delete";
    }

    public Basket createBasket(int clientId){
        Basket basket = new Basket();
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if (optionalClient.isEmpty()) {
            return null;
        }
        Client client = optionalClient.get();
        basket.setClient(client);
        basketRepository.save(basket);
        client.setBasket(basket);
        clientRepository.save(client);
        return basket;
    }

    public Basket getOneByClientId(int id){
        Optional<Basket> optionalBasket = basketRepository.findBasketByClient_Id(id);
        Basket basket = (optionalBasket.isEmpty()) ? createBasket(id) : optionalBasket.get();
        return basket;
    }

    public String addProductInBasket(int clientId, TypeProduct typeProduct, int productId, int quantity){
        Optional<Basket> optionalBasket = basketRepository.findBasketByClient_Id(clientId);
        Basket basket = (optionalBasket.isEmpty()) ? createBasket(clientId) : optionalBasket.get();
        BasketDetail basketDetail = new BasketDetail();
        basketDetail.setBasket(basket);
        basketDetail.setProduct_id(productId);
        basketDetail.setQuantity(quantity);

        if (typeProduct.equals(TypeProduct.BOOKS)) {
            Optional<Book> optionalBook = bookRepository.findById(productId);
            basketDetail.setTypeProduct(TypeProduct.BOOKS);
            if (optionalBook.isEmpty()){
                return "Book not found";
            }
            Book book = optionalBook.get();
            if (book.getAllQuantity() < quantity) {
                return "Do not have " + quantity + " book";
            }
        } else if (typeProduct.equals(TypeProduct.ELECTRONICS)) {
            basketDetail.setTypeProduct(TypeProduct.ELECTRONICS);
            Optional<Telephone> optionalTelephone = telephoneRepository.findById(productId);
            if (optionalTelephone.isEmpty()){
                return "Telephone not found";
            }
            Telephone telephone = optionalTelephone.get();
            if (telephone.getAllQuantity() < quantity) {
                return "Do not have " + quantity + " telephones";
            }
        } else if (typeProduct.equals(TypeProduct.PLUMBING)) {
            basketDetail.setTypeProduct(TypeProduct.PLUMBING);
            Optional<WashingMachine> optionalWashingMachine = washingMachineRepository.findById(productId);
            if (optionalBasket.isEmpty()){
                return "Washing machine not found";
            }
            WashingMachine washingMachine = optionalWashingMachine.get();
            if (washingMachine.getAllQuantity() < quantity) {
                return "Do not have " + quantity + " washing machines";
            }
        }

        basketDetailsRepository.save(basketDetail);
        recalculateTotal(basket.getId());
        return "Save data";
    }

    public String deleteProductInBasket(int basketDetailsId){
        Optional<BasketDetail> optionalBasketDetail = basketDetailsRepository.findById(basketDetailsId);
        if (optionalBasketDetail.isEmpty()){
            return "Data not found";
        }
        BasketDetail basketDetail = optionalBasketDetail.get();
        Basket basket = basketDetail.getBasket();
        basket.setBasketDetails(basket.getBasketDetails().stream().filter(product ->
                product.getId() != basketDetailsId).collect(Collectors.toSet()));
        basketRepository.save(basket);
        basketDetailsRepository.deleteById(basketDetailsId);
        recalculateTotal(basket.getId());
        return "Data delete";
    }

    public String changeQuantity(int basketDatailId, int newQuantity){
        Optional<BasketDetail> optionalBasketDetail = basketDetailsRepository.findById(basketDatailId);
        if (optionalBasketDetail.isEmpty()){
            return "Data not found";
        }
        BasketDetail basketDetail = optionalBasketDetail.get();
        if (basketDetail.getTypeProduct().equals(TypeProduct.BOOKS)){
            Book book = bookRepository.findById(basketDetail.getProduct_id()).get();
            if (book.getAllQuantity() < newQuantity){
                return "Do not have " + newQuantity + " books.";
            }

        } else if (basketDetail.getTypeProduct().equals(TypeProduct.ELECTRONICS)) {
            Telephone telephone = telephoneRepository.findById(basketDetail.getProduct_id()).get();
            if (telephone.getAllQuantity() < newQuantity) {
                return "Do not have " + newQuantity + " telephones";
            }

        } else if (basketDetail.getTypeProduct().equals(TypeProduct.PLUMBING)) {
            WashingMachine washingMachine = washingMachineRepository.findById(basketDetail.getProduct_id()).get();
            if (washingMachine.getAllQuantity() + basketDetail.getQuantity() < newQuantity) {
                return "Do not have " + newQuantity + " washing machines";
            }
        }
        basketDetail.setQuantity(newQuantity);
        basketDetailsRepository.save(basketDetail);
        recalculateTotal(basketDetail.getBasket().getId());
        return "Data update";
    }

    public void recalculateTotal(int basketId) {
        List<BasketDetail> products = basketDetailsRepository.findBasketDetailsByBasket_Id(basketId);
        Basket basket = basketRepository.findById(basketId).get();
        int sum = 0;
        for (var i : products) {
            if (i.getTypeProduct() == TypeProduct.BOOKS) {
                Book book = bookRepository.findById(i.getProduct_id()).get();
                sum += book.getPrice() * i.getQuantity();
            } else if (i.getTypeProduct() == TypeProduct.ELECTRONICS) {
                Telephone telephone = telephoneRepository.findById(i.getProduct_id()).get();
                sum += telephone.getPrice() * i.getQuantity();
            } else if (i.getTypeProduct() == TypeProduct.PLUMBING) {
                WashingMachine washingMachine = washingMachineRepository.findById(i.getProduct_id()).get();
                sum += washingMachine.getPrice() * i.getQuantity();
            }
        }
        basket.setTotal(sum);
        basketRepository.save(basket);
    }

    public String checkQuantity(int basketId) {
        List<BasketDetail> products = basketDetailsRepository.findBasketDetailsByBasket_Id(basketId);
        for (var i : products) {
            if (i.getTypeProduct() == TypeProduct.BOOKS) {
                Book book = bookRepository.findById(i.getProduct_id()).get();
                if (book.getAllQuantity() < i.getQuantity()){
                    return "Not enough book id " + i.getProduct_id();
                }
            } else if (i.getTypeProduct() == TypeProduct.ELECTRONICS) {
                Telephone telephone = telephoneRepository.findById(i.getProduct_id()).get();
                if (telephone.getAllQuantity() < i.getQuantity()){
                    return "Not enough telephone id " + i.getProduct_id();
                }
            } else if (i.getTypeProduct() == TypeProduct.PLUMBING) {
                WashingMachine washingMachine = washingMachineRepository.findById(i.getProduct_id()).get();
                if (washingMachine.getAllQuantity() < i.getQuantity()){
                    return "Not enough washing machine id " + i.getProduct_id();
                }
            }
        }
        return "";
    }

    public String buy(int clientId) {
        Basket basket = basketRepository.findBasketByClient_Id(clientId).get();
        String result = this.checkQuantity(basket.getId());
        if (!result.equals(""))
            return result;
        List<BasketDetail> products = basketDetailsRepository.findBasketDetailsByBasket_Id(basket.getId());
        for (var i : products) {
            if (i.getTypeProduct() == TypeProduct.BOOKS) {
                Book book = bookRepository.findById(i.getProduct_id()).get();
                int tempQuantity = book.getAllQuantity() - i.getQuantity();
                book.setAllQuantity(tempQuantity);
                bookRepository.save(book);
            } else if (i.getTypeProduct() == TypeProduct.ELECTRONICS) {
                Telephone telephone = telephoneRepository.findById(i.getProduct_id()).get();
                int tempQuantity = telephone.getAllQuantity() - i.getQuantity();
                telephone.setAllQuantity(tempQuantity);
                telephoneRepository.save(telephone);
            } else if (i.getTypeProduct() == TypeProduct.PLUMBING) {
                WashingMachine washingMachine = washingMachineRepository.findById(i.getProduct_id()).get();
                int tempQuantity = washingMachine.getAllQuantity() - i.getQuantity();
                washingMachine.setAllQuantity(tempQuantity);
                washingMachineRepository.save(washingMachine);
            }
        }
        int total = basket.getTotal();
        basket.getBasketDetails().clear();
        products.forEach(product -> basketDetailsRepository.deleteById(product.getId()));
        this.recalculateTotal(basket.getId());

        return "Order done, total: " + total + ".";
    }

    public Iterable<BasketDetail> getDet(){
        return basketDetailsRepository.findAll();
    }
}
