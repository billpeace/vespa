// Copyright 2017 Yahoo Holdings. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.
package com.yahoo.config;

import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * Represents a 'file' in a {@link ConfigInstance}, usually a filename.
 *
 * @author gjoranv
 */
public class FileNode extends LeafNode<FileReference> {

    public FileNode() {
    }

    public FileNode(String stringVal) {
        super(true);
        this.value = new FileReference(ReferenceNode.stripQuotes(stringVal));
    }

    public FileReference value() {
        return value;
    }

    @Override
    public String getValue() {
        return value.value();
    }

    @Override
    public String toString() {
        return (value == null) ? "(null)" : '"' + getValue() + '"';
    }

    @Override
    protected boolean doSetValue(@NonNull String stringVal) {
        value = new FileReference(ReferenceNode.stripQuotes(stringVal));
        return true;
    }


}
