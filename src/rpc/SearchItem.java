package rpc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import db.DBConnectionFactory;
import entity.Item;
import external.TicketMasterAPI;

/**
 * Servlet implementation class SearchItem
 */
@WebServlet("/search")
public class SearchItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		// print the username based on URL 
//		PrintWriter out = response.getWriter();
//
//		if(request.getParameter("username") != null) {
//			String username = request.getParameter("username");
//			out.print("Hello " + username);
//		}
		 
		// response a HTML file 
//		  response.setContentType("text/html");
//          PrintWriter out = response.getWriter();
//         
//          out.println("<html><body>");
//          out.println("<h1>This is a HTML page</h1>");
//          out.println("</body></html>");
//         
//          out.close();        		
		
		// response a JSON 
//		  response.setContentType("application/json");
//          PrintWriter out = response.getWriter();
//         
//          String username = "";
//          if (request.getParameter("username") != null) {
//                      username = request.getParameter("username");
//          }
//          JSONObject obj = new JSONObject();
//          try {
//                      obj.put("username", username);
//          } catch (JSONException e) {
//                      e.printStackTrace();
//          }
//          out.print(obj);
//          out.close();   
		
		  String userId = request.getParameter("user_id");
		  double lat = Double.parseDouble(request.getParameter("lat"));
          double lon = Double.parseDouble(request.getParameter("lon"));
          // Term can be empty or null.
          String term = request.getParameter("term");

          DBConnection connection = DBConnectionFactory.getConnection();
          List<Item> items = connection.searchItems(lat, lon, term);
          connection.close();
          
          Set<String> favorite = connection.getFavoriteItemIds(userId);
  		  connection.close();


          List<JSONObject> list = new ArrayList<>();
          try {
                      for (Item item : items) {
                                  // Add a thin version of item object
                                  JSONObject obj = item.toJSONObject();
                                  // check if this is a favorite one
                                  // this filed is required by frontend to correctly display favorite items
                                  obj.put("favorite", favorite.contains(item.getItemId()));
                                  list.add(obj);
                      }
          } catch (Exception e) {
                      e.printStackTrace();
          }
          JSONArray array = new JSONArray(list);
          RpcHelper.writeJsonArray(response, array);
	}
          
          
          
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
