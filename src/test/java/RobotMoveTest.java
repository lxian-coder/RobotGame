import com.darcyxian.robotchallenge.RobotMove;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Darcy Xian  30/7/22  7:03 am      RobotChallenge
 */
@ExtendWith(MockitoExtension.class)
public class RobotMoveTest {

    RobotMove robotMove = new RobotMove();
    @Test
    void shouldThrowExceptionWhenXOrYOutOfRange(){
        int x1 = 5;
        int y1 = 5;
        int x2 = -1;
        int y2 = -1;
        
    }

}
