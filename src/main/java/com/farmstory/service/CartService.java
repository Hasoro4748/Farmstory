package com.farmstory.service;

import com.farmstory.dto.CartDTO;
import com.farmstory.dto.CartResponceDTO;
import com.farmstory.dto.ProductDTO;
import com.farmstory.entity.Cart;
import com.farmstory.entity.Product;
import com.farmstory.entity.prodCate;
import com.farmstory.repository.CartRepository;
import com.farmstory.repository.ProductRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class CartService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    public CartDTO selectCartByCartNo(int cartNo) {
        Optional<Cart> cart = cartRepository.findById(cartNo);
        if (cart.isPresent()) {
            CartDTO cartDTO = modelMapper.map(cart.get(), CartDTO.class);
            return cartDTO;
        }
        return null;
    }
    public void deleteCartByCartNo(int cartNo) {
        cartRepository.deleteById(cartNo);
    }
    /*
    public boolean insertCart(CartDTO cartDTO){
        Cart cart = modelMapper.map(cartDTO, Cart.class);
        log.info("Insert cart " + cartDTO);
        Product product = Product.builder()
                .pNo(cartDTO.getProdNo().getPNo())
                .build();
        cart.setProdNo(product);
        Cart savedCart = cartRepository.save(cart);
        if(savedCart.getCartNo() != 0){
            return true;
        }else{
            return false;
        }
    }
    public List<CartResponceDTO> selectALLWithUid(String uid){
        List<Tuple> cartList = cartRepository.findByCartNo(uid);
        List<CartResponceDTO> cartResponceDTOList = cartList.stream().map(tuple -> {
            Cart cart = tuple.get(0, Cart.class);
            return CartResponceDTO.builder()
                    .cartNo(cart.getCartNo())
                    .cartProdQty(cart.getCartProdQty())
                    .cartProdDate(cart.getCartProdDate())
                    .uid(uid)
                    .prodNo(cart.getProdNo().getPNo())
                    .pName(cart.getProdNo().getPName())
                    .discount(cart.getProdNo().getDiscount())
                    .point(cart.getProdNo().getPoint())
                    .price(cart.getProdNo().getPrice())
                    //임시@@@@@@@@@@@@
                    .prodCateName("과일")
                    .build();
        }).toList();

        return cartResponceDTOList;
    }
*/
    public boolean insertCart1(CartDTO cartDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (authentication != null && authentication.getPrincipal() instanceof UserDetails)
                ? ((UserDetails) authentication.getPrincipal()).getUsername()
                : null;

        log.info("현재 로그인한 사용자 uid: " + uid);

        cartDTO.setUid(uid);

        Cart cart = modelMapper.map(cartDTO, Cart.class);
        log.info("Insert cart " + cartDTO);

        Cart savedCart = cartRepository.save(cart);
        if(savedCart.getCartNo() != 0){
            return true;
        }else{
            return false;
        }
    }
    public List<CartDTO> findCartWithUid(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (authentication != null && authentication.getPrincipal() instanceof UserDetails)
                ? ((UserDetails) authentication.getPrincipal()).getUsername()
                : null;

        List<Cart> cartList = cartRepository.findAllByUid(uid);
        List<CartDTO> cartDTOList = cartList.stream().map(cart -> {
            CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

            Tuple tuple = productRepository.selectProductByPId(cartDTO.getProdNo());

            Product product = tuple.get(0, Product.class);
            String p_sName1 = (tuple.get(1, String.class));
            String p_sName2 = (tuple.get(2, String.class));
            String p_sName3 = (tuple.get(3, String.class));
            if(p_sName1 == null){
                p_sName1 = "404이미지없음.png";
            }if(p_sName2 == null){
                p_sName2 = "404이미지없음.png";
            }if(p_sName3 == null){
                p_sName3= "404이미지없음.png";
            }
            prodCate prodCate = tuple.get(4, prodCate.class);
            product.setP_sName1(p_sName1);
            product.setP_sName2(p_sName2);
            product.setP_sName3(p_sName3);
            product.setProdCate(prodCate);
            ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);

            cartDTO.setProductDTO(productDTO);
            return cartDTO;
        }).toList();

        return cartDTOList;
    }

}
