/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Илья
 */
public class InexchangeableSpacesException extends Exception{
    String message;

    public InexchangeableSpacesException() {}
    public InexchangeableSpacesException(String message) {
        this.message = message;
    }

    public String toString() {
        return "Exception InexchangeableSpacesException: " + message;
    }
}
