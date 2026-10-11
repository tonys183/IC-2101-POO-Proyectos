package Objects.Cupones;
import Entidades.Player;
public interface CuponStrategy {
    void apply(Player player);
    void unapply(Player  player);
}
