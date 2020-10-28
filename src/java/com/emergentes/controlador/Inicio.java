package com.emergentes.controlador;

import com.emergentes.dao.ProductoDAO;
import com.emergentes.dao.ProductoDAOimpl;
import com.emergentes.modelo.Producto;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Inicio", urlPatterns = {"/Inicio"})
public class Inicio extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       try{
           ProductoDAO dao= new ProductoDAOimpl();
           int id;
           Producto pr= new Producto();
           String action=(request.getParameter("action")!=null)? request.getParameter("action"):"view";
           switch (action){
                case "add":
                   request.setAttribute("producto",pr);
                   request.getRequestDispatcher("frmasivo.jsp").forward(request, response);
                   break;
                case "edit":
                    id = Integer.parseInt(request.getParameter("id"));
                    pr=dao.getById(id);
                    System.out.println(pr);
                    request.setAttribute("producto",pr);
                    request.getRequestDispatcher("frmasivo.jsp").forward(request, response);
                    break;
                case "delete":
                    id = Integer.parseInt(request.getParameter("id"));
                    dao.delete(id);
                    request.getRequestDispatcher("Inicio").forward(request, response);
                    break;
                default:
                    List<Producto> lista = dao.getAll();
                    request.setAttribute("producto",lista);
                    request.getRequestDispatcher("listado.jsp").forward(request, response);
                    break;
           }
       } catch (Exception ex) {
            System.out.println("Error: "+ ex.getMessage());
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       int id = Integer.parseInt(request.getParameter("id"));
       String producto=request.getParameter("producto");
       float precio=Float.parseFloat(request.getParameter("precio"));
       int cantidad =Integer.parseInt(request.getParameter("cantidad"));
       ProductoDAO dao=new ProductoDAOimpl();
       Producto pr = new Producto();
       pr.setId(id);
       pr.setProducto(producto);
       pr.setPrecio(precio);
       pr.setCantidad(cantidad);
       if(id==0){
           try{
              
               dao.insert(pr);
               response.sendRedirect("Inicio");
           }catch(Exception ex){
               System.out.println("Error: "+ex.getMessage());
           }
       }else{
           try{
               
               dao.update(pr);
               response.sendRedirect("Inicio");
           }catch(Exception ex){
               System.out.println("Error: "+ex.getMessage());
           }
       }
    }

}
