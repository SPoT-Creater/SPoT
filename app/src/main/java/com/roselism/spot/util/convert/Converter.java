package com.roselism.spot.util.convert;

import java.io.FileNotFoundException;

/**
 * Created by simon on 2016/4/26.
 */
public interface Converter<P, R> {
    R convert(P parameter) throws FileNotFoundException;
}