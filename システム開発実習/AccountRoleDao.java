package jp.co.persol.crm.hitolink.main.model.dao;


	import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.persol.crm.hitolink.main.model.dto.AccountRole;

public class AccountRoleDao extends CommonDao{

    public AccountRoleDao(){
    }

  //アカウントロール取得処理
    public List<AccountRole> AccountRoleList(String tenantId){
        // DBへアクセスしてロールを全て取得する。
		Connection conn = getConnection();
        PreparedStatement pstmt = null;
        List<AccountRole> roleList = new ArrayList<AccountRole>();

        try{
	    String sql = "SELECT account_role_id, role_name "
	    			+ "FROM account_role "
	    			+ "WHERE tenant_id = ?";

	    pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1, tenantId);
	    ResultSet rs = pstmt.executeQuery();
	    while (rs.next()) {
	    	AccountRole role = new AccountRole(rs.getString("account_role_id"),
	    			rs.getString("role_name"));
	    	roleList.add(role);
	    }
        } catch (SQLException e) {
        	e.printStackTrace();
	    } finally {
	    	closeConnection(conn);
	    }
        return roleList;
    }
}
