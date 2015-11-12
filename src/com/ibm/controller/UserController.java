package com.ibm.controller;

import com.ibm.dao.UserDAO;
import com.ibm.model.User;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * Classe Controller que receberá as chamadas de cadastro de usuário
 */

public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserDAO dao;

    public UserController() {
        super();
        dao = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
   
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
        
    	User user = new User();
    	RequestDispatcher view;
    	
    	user.setEmail(request.getParameter("email"));
    	user.setPassword(request.getParameter("password"));
        
    	// caso seja solicitado o cadastro do usuário
        if(request.getParameter("action").toString().equals("cadastrar"))
        {
        	user.setFirstName(request.getParameter("firstName"));
        	user.setLastName(request.getParameter("lastName"));
        	user.setAddress(request.getParameter("address"));
        	user.setSex(request.getParameter("sex"));
            dao.addUser(user);
            request.setAttribute("msg", "Cadastro realizado com sucesso!\nPor favor realize o login");
            view = request.getRequestDispatcher("/login.jsp");
        }
        else
        {
        	// caso seja apenas solocitado o login do usuario
        	if(request.getParameter("action").toString().equals("entrar"))
        	{
        		int result = dao.loginUser(user);
        		
        		if(result == 1){
        			HttpSession session = request.getSession();
        			//cria a sessao na pagina, permitindo o usuario visitar as outras paginas
        			session.setAttribute("user", user.getEmail());
        			request.setAttribute("msg", "Login realizado com sucesso");
        			view = request.getRequestDispatcher("/index.jsp");
        		}
        		else{
        			request.setAttribute("msg", "Login falhou. Tente novamente");
        			view = request.getRequestDispatcher("/login.jsp");
        		}
        	}
        	else
        		view = request.getRequestDispatcher("/index.jsp");
        }
        
        view.forward(request, response);
    }
}