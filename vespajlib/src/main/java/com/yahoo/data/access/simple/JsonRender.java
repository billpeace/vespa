// Copyright 2017 Yahoo Holdings. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.
package com.yahoo.data.access.simple;

import com.yahoo.text.DoubleFormatter;
import com.yahoo.data.access.*;

/**
 * Encodes json from an inspectable object.
 *
 * @author arnej27959
 */
public final class JsonRender
{
    public static StringBuilder render(Inspectable value,
                                       StringBuilder target,
                                       boolean compact)
    {
        StringEncoder enc = new StringEncoder(target, compact);
        enc.encode(value.inspect());
        return target;
    }

    public static final class StringEncoder implements ArrayTraverser, ObjectTraverser
    {
        private final StringBuilder out;
        private boolean head = true;
        private boolean compact;
        private int level = 0;

        public StringEncoder(StringBuilder out, boolean compact) {
            this.out = out;
            this.compact = compact;
        }

        public void encode(Inspector top) {
            encodeValue(top);
            if (!compact) {
                out.append('\n');
            }
        }

        private void encodeEMPTY() {
            out.append("null");
        }

        private void encodeBOOL(boolean value) {
            out.append(value ? "true" : "false");
        }

        private void encodeLONG(long value) {
            out.append(String.valueOf(value));
        }

        private void encodeDOUBLE(double value) {
            if (Double.isNaN(value) || Double.isInfinite(value)) {
                out.append("null");
            } else {
                out.append(DoubleFormatter.stringValue(value));
            }
        }

        static final char[] hex = "0123456789ABCDEF".toCharArray();

        private void encodeSTRING(String value) {
            out.append('"');
            for (char c : value.toCharArray()) {
                switch (c) {
                case '"':  out.append('\\').append('"'); break;
                case '\\': out.append('\\').append('\\'); break;
                case '\b': out.append('\\').append('b'); break;
                case '\f': out.append('\\').append('f'); break;
                case '\n': out.append('\\').append('n'); break;
                case '\r': out.append('\\').append('r'); break;
                case '\t': out.append('\\').append('t'); break;
                default:
                    if (c > 0x1f && c < 127) {
                        out.append(c);
                    } else { // requires escaping according to RFC 4627
                        out.append('\\').append('u');
                        out.append(hex[(c >> 12) & 0xf]);
                        out.append(hex[(c >> 8) & 0xf]);
                        out.append(hex[(c >> 4) & 0xf]);
                        out.append(hex[c & 0xf]);
                    }
                }
            }
            out.append('"');
        }

        private void encodeDATA(byte[] value) {
            out.append('"');
            out.append("0x");
            for (int pos = 0; pos < value.length; pos++) {
                out.append(hex[(value[pos] >> 4) & 0xf]);
                out.append(hex[value[pos] & 0xf]);
            }
            out.append('"');
        }

        private void encodeARRAY(Inspector inspector) {
            openScope("[");
            ArrayTraverser at = this;
            inspector.traverse(at);
            closeScope("]");
        }

        private void encodeOBJECT(Inspector inspector) {
            openScope("{");
            ObjectTraverser ot = this;
            inspector.traverse(ot);
            closeScope("}");
        }

        private void openScope(String opener) {
            out.append(opener);
            level++;
            head = true;
        }

        private void closeScope(String closer) {
            level--;
            separate(false);
            out.append(closer);
        }

        private void encodeValue(Inspector inspector) {
            switch(inspector.type()) {
            case EMPTY:  encodeEMPTY();                      return;
            case BOOL:   encodeBOOL(inspector.asBool());     return;
            case LONG:   encodeLONG(inspector.asLong());     return;
            case DOUBLE: encodeDOUBLE(inspector.asDouble()); return;
            case STRING: encodeSTRING(inspector.asString()); return;
            case DATA:   encodeDATA(inspector.asData());     return;
            case ARRAY:  encodeARRAY(inspector);             return;
            case OBJECT: encodeOBJECT(inspector);            return;
            }
            assert false : "Should not be reached";
        }

        private void separate(boolean useComma) {
            if (!head && useComma) {
                out.append(',');
            } else {
                head = false;
            }
            if (!compact) {
                out.append("\n");
                for (int lvl = 0; lvl < level; lvl++) { out.append(" "); }
            }
        }

        public void entry(int idx, Inspector inspector) {
            separate(true);
            encodeValue(inspector);
        }

        public void field(String name, Inspector inspector)  {
            separate(true);
            encodeSTRING(name);
            out.append(':');
            if (!compact)
                out.append(' ');
            encodeValue(inspector);
        }
    }
}
