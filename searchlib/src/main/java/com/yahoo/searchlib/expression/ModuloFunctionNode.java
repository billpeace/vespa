// Copyright 2017 Yahoo Holdings. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.
package com.yahoo.searchlib.expression;

/**
 * This function is an instruction to modulo the arguments in order.
 *
 * @author baldersheim
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen</a>
 */
public class ModuloFunctionNode extends NumericFunctionNode {

    public static final int classId = registerClass(0x4000 + 64, ModuloFunctionNode.class);

    @Override
    protected int onGetClassId() {
        return classId;
    }

    @Override
    protected void onArgument(final ResultNode arg, ResultNode result) {
        ((NumericResultNode)result).modulo(arg);
    }
}
