package br.ufpi.mapper.conexaobd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsavel pela Conexao ao banco de dados setado.
 * 
 * @author Alcemir
 *
 */
public class Conexao {
	static String status = "";
	static String bd = "information_schema";
	static String bd_user = "root";
	static String bd_user_password = "";
	
	/**
	 * Retorna o banco de dados setado.
	 * @return
	 */
	public static String getBd() {
		return bd;
	}

	/**
	 * Seta o banco de dados.
	 * @param bd
	 */
	public static void setBd(String bd) {
		Conexao.bd = bd;
	}

	/**
	 * Retorna o usu�rio do banco de dados.
	 * @return
	 */
	public static String getBd_user() {
		return bd_user;
	}

	/**
	 * Seta o usu�rio do banco de dados.
	 * @param bdUser
	 */
	public static void setBd_user(String bdUser) {
		bd_user = bdUser;
	}

	/**
	 * Retorna o passoword do usu�rio desta conexao.
	 * @return
	 */
	public static String getBd_user_password() {
		return bd_user_password;
	}

	/**
	 * Seta o password do usu�rio desta conexao.
	 * @param bdUserPassword
	 */
	public static void setBd_user_password(String bdUserPassword) {
		bd_user_password = bdUserPassword;
	}
	
	/**
	 * Cria e retorna uma conexao feita com os dados setados anteriomente.
	 * @return con
	 */
	public static Connection getConnection(){
		Connection con = null;
 
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();//pega a classe Driver
				String url = "jdbc:mysql://localhost/"+bd+"?user="+bd_user+"&password="+bd_user_password;//publico � o schema, root o usuario, e no meu caso nao tem senha.
				con = DriverManager.getConnection(url);//conecta ao banco.
				status = "Conexão Aberta";//muda o status para conex�o aberta.
			} catch (ClassNotFoundException e) {
				status = e.getMessage();
			} catch (SQLException e) {
				status = e.getMessage();
			} catch (Exception e){
				status = e.getMessage();
			}
 
		return con;//retorna a conexao.
	}

	/**
	 * Retorna a mensagem de estatus desta conexao.
	 * @return
	 */
	public static String getStatus() {
		return status;
	}
}
