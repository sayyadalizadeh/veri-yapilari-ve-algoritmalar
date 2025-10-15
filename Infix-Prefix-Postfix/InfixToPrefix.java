public class InfixToPrefix {

    // Char yığın
    static class CStack {
        char[] a = new char[100];
        int top = -1;

        void push(char c) {
            a[++top] = c;
        }

        char pop() {
            return a[top--];
        }

        char peek() {
            return a[top];
        }

        boolean isEmpty() {
            return top == -1;
        }
    }

    static boolean isOp(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    static int prec(char c) {
        if (c == '^') return 3;
        if (c == '*' || c == '/') return 2;
        if (c == '+' || c == '-') return 1;
        return 0;
    }

    static boolean rightAssoc(char c) {
        return c == '^';
    }

    // Yardımcı: stringi ters çevirip parantezleri değiştir
    static String reverseSwap(String s) {
        StringBuilder b = new StringBuilder();
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == '(') {
                c = ')';
            } else if (c == ')') {
                c = '(';
            }
            b.append(c);
        }
        return b.toString();
    }

    // İnfix → Prefix: ters çevir + postfix’e çevir + tekrar ters çevir
    static String toPrefix(String s) {
        String r = reverseSwap(s);

        CStack st = new CStack();
        StringBuilder out = new StringBuilder();

        for (char ch : r.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                out.append(ch);
            } else if (ch == '(') {
                st.push(ch);
            } else if (ch == ')') {
                while (!st.isEmpty() && st.peek() != '(') {
                    out.append(st.pop());
                }
                if (!st.isEmpty()) {
                    st.pop();
                }
            } else if (isOp(ch)) {
                while (!st.isEmpty() && isOp(st.peek())) {
                    char t = st.peek();
                    boolean lower = prec(ch) < prec(t);
                    boolean equalLeftAssoc = prec(ch) == prec(t) && !rightAssoc(ch);
                    if (lower || equalLeftAssoc) {
                        out.append(st.pop());
                    } else {
                        break;
                    }
                }
                st.push(ch);
            }
        }

        while (!st.isEmpty()) {
            out.append(st.pop());
        }

        // postfix sonucu ters çevir → prefix
        return new StringBuilder(out.toString()).reverse().toString();
    }

    public static void main(String[] args) {
        String infix = "(A+B)*C-D/E";
        System.out.println("Infix  : " + infix);
        System.out.println("Prefix : " + toPrefix(infix)); // -*+ABC/DE
    }
}
