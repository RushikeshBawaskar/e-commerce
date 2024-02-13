package com.bookcharm.app.controller;

import com.bookcharm.app.dto.CartDto;
import com.bookcharm.app.exception.ClientErrorException;
import com.bookcharm.app.exception.UnauthorizedAccessException;
import com.bookcharm.app.model.ShoppingCart;
import com.bookcharm.app.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/shopping-carts")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("")
    public ResponseEntity<ShoppingCart> getShoppingCartByUserId(@RequestHeader String Authorization) {



        String jwtToken = Authorization;
        System.out.println("jwtToken using request header : " + Authorization);

        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(jwtToken);

        System.out.println(shoppingCart);

        // return the status accordingly, if user is not authorized
        return shoppingCart != null ? new ResponseEntity<>(shoppingCart, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PutMapping(name = "/shopping-cart")
    public ResponseEntity<Void> updateShoppingCart(HttpServletRequest request, @RequestBody CartDto cartDto){


        String jwtToken = request.getHeader("Authorization");
        System.out.println("This is token : " + jwtToken);



        try{
            shoppingCartService.updateShoppingCart(jwtToken, cartDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(UnauthorizedAccessException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }catch (ClientErrorException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}