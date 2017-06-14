// Copyright 2017 Yahoo Holdings. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.
package com.yahoo.log;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class UncloseableOutputStreamTestCase {

    @Test
    public void requireThatWriteIsProxied() throws IOException {
        OutputStream out = Mockito.mock(OutputStream.class);
        new UncloseableOutputStream(out).write(69);
        Mockito.verify(out).write(69);
    }

    @Test
    public void requireThatCloseIsIgnored() {
        OutputStream out = Mockito.mock(OutputStream.class);
        new UncloseableOutputStream(out).close();
        Mockito.verifyNoMoreInteractions(out);
    }
}
