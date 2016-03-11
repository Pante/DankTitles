package com.karus.danktitles;

import java.util.Collection;

/*
 * Copyright (C) 2016 PanteLegacy @ karusmc.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public interface PreconditionChecker {
    
    // Checks if the argument is null and returns true if it is
    public default <T> boolean checkNull(T object) {
        return object == null;
    }
    
    
    // Checks if the argument, a Collection is null or if it's empty and returns true if it is
    public default boolean checkCollection(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }
    
    
    // Checks if the collection contains the object, returns true if it contains the object
    public default <T> boolean checkContain(Collection<T> collection, T object) {
        return (collection.contains(object));
    }
    
}
