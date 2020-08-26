public List<User> users(){
		try(Connection conn = TestConfiguration.connect()){
			String sql ="SELECT * FROM app_user WHERE role_id=3;";
			Statement stmt = conn.createStatement();
			
			List<User> user = new ArrayList<User>();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				u.setId(rs.getInt("user_id"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("password"));
				u.setFirstName(rs.getString("first_name"));
				u.setLastName(rs.getString("last_name"));
				u.setUserRole(rs.getInt("role_id"));
				user.add(u);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}