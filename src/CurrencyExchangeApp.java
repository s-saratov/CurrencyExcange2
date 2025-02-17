import repository.*;
import service.MainService;
import service.MainServiceImpl;
import view.Menu;

public class CurrencyExchangeApp {

    public static void main(String[] args) {

        // Создаём экземпляры классов приложения

        AccountRepository accountRepository = new AccountRepositoryImpl();
        CurrencyRepository currencyRepository = new CurrencyRepositoryImpl();
        RateHistoryRepository rateHistoryRepository = new RateHistoryRepositoryImpl();
        UserRepository userRepository = new UserRepositoryImpl();
        MainService service = new MainServiceImpl(accountRepository, currencyRepository, rateHistoryRepository, userRepository);
        Menu menu = new Menu(service);

        menu.run();

    }

}