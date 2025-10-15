public class EvaluatePrefix {

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

    // Basit prefix değerlendirme (tek haneli sayılar)
    static int eval(String s) {
        IStack st = new IStack();

        for (int i = s.length() - 1; i >= 0; i--) {
            char ch = s.charAt(i);

            if (Character.isDigit(ch)) {
                st.push(ch - '0');
            } else if (isOp(ch)) {
                // Prefix'te sağdan sola giderken: önce sol, sonra sağ operantı çıkar
                int a = st.pop(); // sol
                int b = st.pop(); // sağ
                int r = 0;

                if (ch == '+') {
                    r = a + b;
                } else if (ch == '-') {
                    r = a - b;
                } else if (ch == '*') {
                    r = a * b;
                } else if (ch == '/') {
                    r = a / b;
                } else if (ch == '^') {
                    r = 1;
                    for (int k = 0; k < b; k++) {
                        r *= a;
                    }
                }

                st.push(r);
            }
        }

        return st.pop();
    }

    public static void main(String[] args) {
        String prefix = "-+*23*549"; // (2*3)+(5*4)-9 = 17
        System.out.println("Prefix : " + prefix);
        System.out.println("Değer  : " + eval(prefix)); // 17
    }
}
