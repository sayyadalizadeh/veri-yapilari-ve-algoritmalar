public class EvaluatePostfix {

    // int yığın
    static class IStack {
        int[] a = new int[100];
        int top = -1;

        void push(int x) {
            a[++top] = x;
        }

        int pop() {
            return a[top--];
        }

        boolean isEmpty() {
            return top == -1;
        }
    }

    static boolean isOp(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    // Basit postfix değerlendirme (tek haneli sayılar)
    static int eval(String s) {
        IStack st = new IStack();

        for (char ch : s.toCharArray()) {
            if (Character.isDigit(ch)) {
                // Karakteri sayıya çevir
                st.push(ch - '0');
            } else if (isOp(ch)) {
                // Sıraya dikkat: a (sol), b (sağ)
                int b = st.pop();
                int a = st.pop();
                int r = 0;

                if (ch == '+') {
                    r = a + b;
                } else if (ch == '-') {
                    r = a - b;
                } else if (ch == '*') {
                    r = a * b;
                } else if (ch == '/') {
                    r = a / b; // 0 kontrolü yok (basit versiyon)
                } else if (ch == '^') {
                    // Basit üs alma (tam sayı)
                    r = 1;
                    for (int i = 0; i < b; i++) {
                        r *= a;
                    }
                }

                st.push(r);
            }
        }

        return st.pop();
    }

    public static void main(String[] args) {
        String postfix = "23*54*+9-"; // (2*3)+(5*4)-9 = 6+20-9 = 17
        System.out.println("Postfix : " + postfix);
        System.out.println("Değer   : " + eval(postfix)); // 17
    }
}
