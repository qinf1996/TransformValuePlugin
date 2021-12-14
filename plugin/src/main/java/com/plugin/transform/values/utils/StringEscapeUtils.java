package com.plugin.transform.values.utils;

import com.plugin.transform.values.translate.CharSequenceTranslator;
import com.plugin.transform.values.translate.LookupTranslator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class StringEscapeUtils {
    /**
     * Translator object for escaping Shell command language.
     */
    public static final CharSequenceTranslator ESCAPE;
    static {
        final Map<CharSequence, CharSequence> escapeMap = new HashMap<>(10);
        escapeMap.put("'", "\\'");
        ESCAPE = new LookupTranslator(
                Collections.unmodifiableMap(escapeMap)
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
