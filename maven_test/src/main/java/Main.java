import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Arrays.copyOf;
import static java.util.Arrays.fill;
import static java.util.Arrays.sort;
import static java.util.Collections.reverseOrder;
import static java.util.Collections.sort;
public class Main {
    FastScanner in;
    PrintWriter out;
    int inf = 1 << 20;
    private void solve() throws IOException {
        int n = in.nextInt(), l = in.nextInt(), r = in.nextInt();
        int[] a  = null; /* changed by lty */
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }
        ArrayList<Integer> imp = new ArrayList<>(), unimp = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (in.nextInt() == 1)
                imp.add(a[i]);
            else
                unimp.add(a[i]);
        }
        sort(imp);
        int[] ks = new int[r + 1];
        fill(ks, inf);
        ks[0] = 0;
        for (int i : unimp)
            for (int j = r; j >= i; j--)
                ks[j] = min(ks[j], ks[j - i]);
        int ans = 0, minL = l, cnt = 0, sum = 0;
        for (int i = imp.size() - 1; i >= 0; i--) {
            while (minL <= r && ks[minL] == inf)
                minL++;
            while (cnt <= i && imp.get(cnt) + sum <= r - minL)
                sum += imp.get(cnt++);
            ans = max(ans, cnt + (minL <= r && cnt <= i ? 1 : 0));
            if (cnt + 1 > ans)
                for (int k = minL; k + sum <= r; k++)
                    if (ks[k] < imp.size() - (i + 1)) {
                        ans = cnt + 1;
                        break;
                    }
            for (int k = r; k >= imp.get(i); k--) {
                ks[k] = min(ks[k], ks[k - imp.get(i)] + 1);
                if (l <= k && k < minL && ks[k] != inf)
                    minL = k;
            }
            if (cnt > i)
                sum -= imp.get(--cnt);
        }
        out.println(ans == 0 && minL <= r && ks[minL] < imp.size() ? 1 : ans);
    }
    class FastScanner {
        StringTokenizer st;
        BufferedReader br;
        FastScanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }
        String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }
        boolean hasNext() throws IOException {
            return br.ready() || (st != null && st.hasMoreTokens());
        }
        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
        double nextDouble() throws IOException {
            return Double.parseDouble(next().replace(',', '.'));
        }
        String nextLine() throws IOException {
            return br.readLine();
        }
        boolean hasNextLine() throws IOException {
            return br.ready();
        }
    }
    private void run() throws IOException {
        in = new FastScanner(System.in);         out = new PrintWriter(System.out);
        solve();
        out.flush();
        out.close();
    }
    public static void main(String[] args) throws IOException {
        new Main().run();
    }
}
