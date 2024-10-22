import controller.InhabitantController;
import repository.InhabitantRepository;
import service.InhabitantService;
import view.InhabitantView;

public class App {
    public static void main(String[] args) {
        new InhabitantView(
                new InhabitantController(
                        new InhabitantService(
                                new InhabitantRepository())))
                .start();
    }
}