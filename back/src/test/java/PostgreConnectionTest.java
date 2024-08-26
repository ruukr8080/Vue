



import org.assertj.core.api.Assertions;
import org.assertj.core.api.Fail;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.sql.*;


public class PostgreConnectionTest {


    private String URL = "jdbc:postgresql://localhost:5432/bod";
    private String USERNAME  = "tester";
    private String PASSWORD  = "sa";

    @Test
    @DisplayName("DB 연결 테스트")
    public void ConnectionTest() throws Exception{
        Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Assertions.assertThat(con).isNotNull();
    }
    @Test
    @DisplayName("DB 연결 및 특정 ID의 이름 검증")
    public void ConnectionNamePrintTest() throws Exception {
        // Given
        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement pre = con.createStatement();
             // DB 스키마 이름이 spring , 테이블 명 : bod
             ResultSet rs = pre.executeQuery("SELECT * FROM BOARD where idx = 1")) {
            // When
            String name = null;
            if (rs.next()) {
                name = rs.getString("name");
            }
            // Then
            Assertions.assertThat(name).isEqualTo("Illaoi Quirinus Quirrell");
        } catch (SQLException e) {
            // 예외 처리
            Fail.fail("예외가 발생했습니다: " + e.getMessage());
        }
    }
}
