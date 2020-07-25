import BankClients.Client;
import BankClients.IndividualClient;
import BankClients.LegalClient;
import BankClients.PhysicClient;

public class Loader {
    public static void main(String[] args) {
        System.out.println("Физическое лицо");
        Client physic = new PhysicClient(8000);
        physic.getStatus();
        physic.deposit(500);
        physic.getStatus();
        physic.withdraw(99);
        physic.getStatus();
        System.out.println();

        System.out.println("Юридическое лицо");
        Client legal = new LegalClient(2650000);
        legal.getStatus();
        legal.deposit(500000);
        legal.getStatus();
        legal.withdraw(100000);
        legal.getStatus();
        System.out.println();

        System.out.println("Индивидуальный предприниматель");
        Client indiv = new IndividualClient(440000);
        indiv.getStatus();
        indiv.deposit(80000);
        indiv.getStatus();
        indiv.withdraw(50000);
        indiv.getStatus();
        physic.
    }
}
