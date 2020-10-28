package com.emergentes.dao;

import com.emergentes.modelo.Producto;
import com.emergentes.utiles.ConexionDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOimpl extends ConexionDB implements ProductoDAO {

    @Override
    public void insert(Producto producto) throws Exception {
        try{
            this.conectar();
            PreparedStatement ps = this.conn.prepareStatement("insert into producto (producto,precio,cantidad) values (?,?,?)");
            ps.setString(1,producto.getProducto());
            ps.setFloat(2, producto.getPrecio());
            ps.setInt(3,producto.getCantidad());
            ps.executeUpdate();
        }catch(Exception e){
            throw e;
        }finally{
            this.desconectar();
        }
    }

    @Override
    public void update(Producto producto) throws Exception {
        try{
            this.conectar();
            PreparedStatement ps = this.conn.prepareStatement("update producto set producto=?, precio=?, cantidad=? where id=?");
            ps.setString(1,producto.getProducto());
            ps.setFloat(2, producto.getPrecio());
            ps.setInt(3,producto.getCantidad());
            ps.setInt(4, producto.getId());
            ps.executeUpdate();
        }catch(Exception e){
            throw e;
        }finally{
            this.desconectar();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        try{
            this.conectar();
            PreparedStatement ps = this.conn.prepareStatement("delete from producto where id=?");
            ps.setInt(1,id);
            ps.executeUpdate();
        }catch(Exception e){
            throw e;
        }finally{
            this.desconectar();
        }
    }

    @Override
    public Producto getById(int id) throws Exception {
        Producto pr =new Producto();
        try{
            this.conectar();
            PreparedStatement ps = this.conn.prepareStatement("select * from producto where id=?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                pr.setId(rs.getInt("id"));
                pr.setProducto(rs.getString("producto"));
                pr.setPrecio(rs.getFloat("precio"));
                pr.setCantidad(rs.getInt("cantidad"));
            }
        }catch(Exception e){
            throw e;
        }finally{
            this.desconectar();
        }
        return pr;
    }

    @Override
    public List<Producto> getAll() throws Exception {
        List<Producto> lista= null;
        try{
            this.conectar();
            String sql="select * from producto";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            lista=new ArrayList<Producto>();
            while(rs.next()){
                Producto pr = new Producto();
                pr.setId(rs.getInt("id"));
                pr.setProducto(rs.getString("producto"));
                pr.setPrecio(rs.getFloat("precio"));
                pr.setCantidad(rs.getInt("cantidad"));
                lista.add(pr);
            }

        }catch(Exception e){
            throw e;
        }finally{
            this.desconectar();
        }
        return lista;
    }
    
}
