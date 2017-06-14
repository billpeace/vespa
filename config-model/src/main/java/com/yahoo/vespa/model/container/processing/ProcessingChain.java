// Copyright 2017 Yahoo Holdings. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.
package com.yahoo.vespa.model.container.processing;

import com.yahoo.component.chain.model.ChainSpecification;
import com.yahoo.vespa.model.container.component.chain.Chain;

/**
 * Represents a processing chain in the config model
 *
 * @author  bratseth
 * @since   5.1.6
 */
public class ProcessingChain extends Chain<Processor> {

    public ProcessingChain(ChainSpecification specWithoutInnerProcessors) {
        super(specWithoutInnerProcessors);
    }

}
