/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.karus.danktitles.io;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
@FunctionalInterface
public interface Output<T> {
    public void out(T result);
}
