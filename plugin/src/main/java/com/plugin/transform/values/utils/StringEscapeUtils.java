/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.plugin.transform.values.utils;



import org.apache.commons.text.translate.CharSequenceTranslator;
import org.apache.commons.text.translate.LookupTranslator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * Escapes and unescapes {@code String}s for Java, Java Script, HTML and XML.
 * </p>
 *
 * <p>
 * #ThreadSafe#
 * </p>
 *
 * <p>
 * This code has been adapted from Apache Commons Lang 3.5.
 * </p>
 *
 * @since 1.0
 */
class StringEscapeUtils {
    /**
     * Translator object for escaping Shell command language.
     */
    public static final CharSequenceTranslator ESCAPE;
    static {
        final Map<CharSequence, CharSequence> escapeXsiMap = new HashMap<>(10);
        escapeXsiMap.put("'", "\\'");
        ESCAPE = new LookupTranslator(
                Collections.unmodifiableMap(escapeXsiMap)
        );
    }

    /* Helper functions */

    /**
     * <p>{@code StringEscapeUtils} instances should NOT be constructed in
     * standard programming.</p>
     *
     * <p>Instead, the class should be used as:</p>
     * <pre>StringEscapeUtils.escapeJava("foo");</pre>
     *
     * <p>This constructor is public to permit tools that require a JavaBean
     * instance to operate.</p>
     */
    public StringEscapeUtils() {
        super();
    }

    /**
     * <p>Convenience wrapper for {@link StringBuilder} providing escape methods.</p>
     *
     * <p>Example:</p>
     * <pre>
     * new Builder(ESCAPE_HTML4)
     *      .append("&lt;p&gt;")
     *      .escape("This is paragraph 1 and special chars like &amp; get escaped.")
     *      .append("&lt;/p&gt;&lt;p&gt;")
     *      .escape("This is paragraph 2 &amp; more...")
     *      .append("&lt;/p&gt;")
     *      .toString()
     * </pre>
     *
     */
    public static final class Builder {

        /**
         * StringBuilder to be used in the Builder class.
         */
        private final StringBuilder sb;

        /**
         * CharSequenceTranslator to be used in the Builder class.
         */
        private final CharSequenceTranslator translator;

        /**
         * Builder constructor.
         *
         * @param translator a CharSequenceTranslator.
         */
        private Builder(final CharSequenceTranslator translator) {
            this.sb = new StringBuilder();
            this.translator = translator;
        }

        /**
         * <p>Escape {@code input} according to the given {@link CharSequenceTranslator}.</p>
         *
         * @param input the String to escape
         * @return {@code this}, to enable chaining
         */
        public Builder escape(final String input) {
            sb.append(translator.translate(input));
            return this;
        }

        /**
         * Literal append, no escaping being done.
         *
         * @param input the String to append
         * @return {@code this}, to enable chaining
         */
        public Builder append(final String input) {
            sb.append(input);
            return this;
        }

        /**
         * <p>Return the escaped string.</p>
         *
         * @return The escaped string
         */
        @Override
        public String toString() {
            return sb.toString();
        }
    }

    /**
     * Get a {@link Builder}.
     * @param translator the text translator
     * @return {@link Builder}
     */
    public static Builder builder(final CharSequenceTranslator translator) {
        return new Builder(translator);
    }


    public static final String escape(final String input) {
        return ESCAPE.translate(input);
    }

}
