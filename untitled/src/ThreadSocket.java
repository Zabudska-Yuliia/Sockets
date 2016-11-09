import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ThreadSocket extends Thread {
    private Socket socket;
    ThreadSocket(Socket socket) {
        this.socket = socket;
        this.start();
    }
    @Override
    public void run() {
        try {
            InputStream is = socket.getInputStream();
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String line;
            // looks for post data
            int postDataI = -1;
            while ((line = in.readLine()) != null && (line.length() != 0)) {
                if (line.contains("Content-Length:")) {
                    postDataI = Integer.parseInt(line.substring(
                            line.indexOf("Content-Length:") + 16,
                            line.length()));
                }
            }
            String postData = "";
            // read the post data
            if (postDataI > 0) {
                char[] charArray = new char[postDataI];
                in.read(charArray, 0, postDataI);
                postData = new String(charArray);
                postData = postData.substring(5);
            }
            // Send the HTML page
            out.println("<H1 style=\"font-family: roboto;\">Welcome you are " + postData + "</H1>");
            out.println("<form name=\"input\" action=\"form_submited\" method=\"post\">");
            out.println("<input style=\"border-radius:5px; padding:10px\" placeholder=\"username\" type=\"text\" name=\"user\"><input style=\"border-radius:5px; background-color:#009999; padding:10px; margin:5px;\" type=\"submit\" value=\"Great me!\"></form>");
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
