/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.karus.danktitles.io;

/**
 *
 * @author PanteLegacy @ karusmc.com
 * @param <T> The return type
 * @param <E> An exception
 */
@FunctionalInterface
public interface Output<T, E> {
    public void out(T result, E exception);
}
