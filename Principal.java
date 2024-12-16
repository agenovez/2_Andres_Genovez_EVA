import java.util.*;

// Clase base CuentaBancaria
abstract class CuentaBancaria {
    protected String numeroCuenta;
    protected String titular;
    protected double saldo;

    public CuentaBancaria(String numeroCuenta, String titular, double saldo) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = saldo;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void depositar(double monto) {
        if (monto > 0) {
            saldo += monto;
        }
    }

    public void retirar(double monto) {
        if (monto > 0 && monto <= saldo) {
            saldo -= monto;
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }

    public void mostrarDetalles() {
        System.out.println("Numero de Cuenta: " + numeroCuenta);
        System.out.println("Titular: " + titular);
        System.out.println("Saldo: " + saldo);
    }

    public abstract double calcularSaldoFinal();
}

// Subclase CuentaAhorro
class CuentaAhorro extends CuentaBancaria {
    private double tasaInteres;

    public CuentaAhorro(String numeroCuenta, String titular, double saldo, double tasaInteres) {
        super(numeroCuenta, titular, saldo);
        this.tasaInteres = tasaInteres;
    }

    @Override
    public double calcularSaldoFinal() {
        return saldo + (saldo * tasaInteres / 100);
    }
}

// Subclase CuentaCorriente
class CuentaCorriente extends CuentaBancaria {
    private double limiteCredito;

    public CuentaCorriente(String numeroCuenta, String titular, double saldo, double limiteCredito) {
        super(numeroCuenta, titular, saldo);
        this.limiteCredito = limiteCredito;
    }

    @Override
    public void retirar(double monto) {
        if (monto > 0 && (saldo + limiteCredito) >= monto) {
            saldo -= monto;
        } else {
            System.out.println("Límite de crédito excedido.");
        }
    }

    @Override
    public double calcularSaldoFinal() {
        return saldo;
    }
}

// Clase GestorBanco
class GestorBanco {
    private Map<String, CuentaBancaria> cuentas = new HashMap<>();

    public void agregarCuenta(CuentaBancaria cuenta) {
        cuentas.put(cuenta.getNumeroCuenta(), cuenta);
    }

    public void consultarCuenta(String numeroCuenta) {
        CuentaBancaria cuenta = cuentas.get(numeroCuenta);
        if (cuenta != null) {
            cuenta.mostrarDetalles();
        } else {
            System.out.println("Cuenta no encontrada.");
        }
    }

    public void realizarDeposito(String numeroCuenta, double monto) {
        CuentaBancaria cuenta = cuentas.get(numeroCuenta);
        if (cuenta != null) {
            cuenta.depositar(monto);
            System.out.println("Depósito realizado.");
        } else {
            System.out.println("Cuenta no encontrada.");
        }
    }

    public void realizarRetiro(String numeroCuenta, double monto) {
        CuentaBancaria cuenta = cuentas.get(numeroCuenta);
        if (cuenta != null) {
            cuenta.retirar(monto);
        } else {
            System.out.println("Cuenta no encontrada.");
        }
    }

    public void calcularSaldoFinal(String numeroCuenta) {
        CuentaBancaria cuenta = cuentas.get(numeroCuenta);
        if (cuenta != null) {
            System.out.println("Saldo final: " + cuenta.calcularSaldoFinal());
        } else {
            System.out.println("Cuenta no encontrada.");
        }
    }
}

// Clase Principal
public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestorBanco gestorBanco = new GestorBanco();

        while (true) {
            System.out.println("\nMenu Principal:");
            System.out.println("1. Registrar nueva cuenta");
            System.out.println("2. Consultar cuenta");
            System.out.println("3. Realizar depósito");
            System.out.println("4. Realizar retiro");
            System.out.println("5. Calcular saldo final");
            System.out.println("6. Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese tipo de cuenta (1: Ahorro, 2: Corriente):");
                    int tipoCuenta = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Ingrese numero de cuenta:");
                    String numeroCuenta = scanner.nextLine();

                    System.out.println("Ingrese titular de la cuenta:");
                    String titular = scanner.nextLine();

                    System.out.println("Ingrese saldo inicial:");
                    double saldo = scanner.nextDouble();

                    if (tipoCuenta == 1) {
                        System.out.println("Ingrese tasa de interes:");
                        double tasaInteres = scanner.nextDouble();
                        gestorBanco.agregarCuenta(new CuentaAhorro(numeroCuenta, titular, saldo, tasaInteres));
                    } else if (tipoCuenta == 2) {
                        System.out.println("Ingrese limite de credito:");
                        double limiteCredito = scanner.nextDouble();
                        gestorBanco.agregarCuenta(new CuentaCorriente(numeroCuenta, titular, saldo, limiteCredito));
                    } else {
                        System.out.println("Tipo de cuenta no valido.");
                    }
                    break;
                case 2:
                    System.out.println("Ingrese numero de cuenta:");
                    numeroCuenta = scanner.nextLine();
                    gestorBanco.consultarCuenta(numeroCuenta);
                    break;
                case 3:
                    System.out.println("Ingrese numero de cuenta:");
                    numeroCuenta = scanner.nextLine();
                    System.out.println("Ingrese monto a depositar:");
                    double montoDeposito = scanner.nextDouble();
                    gestorBanco.realizarDeposito(numeroCuenta, montoDeposito);
                    break;
                case 4:
                    System.out.println("Ingrese numero de cuenta:");
                    numeroCuenta = scanner.nextLine();
                    System.out.println("Ingrese monto a retirar:");
                    double montoRetiro = scanner.nextDouble();
                    gestorBanco.realizarRetiro(numeroCuenta, montoRetiro);
                    break;
                case 5:
                    System.out.println("Ingrese numero de cuenta:");
                    numeroCuenta = scanner.nextLine();
                    gestorBanco.calcularSaldoFinal(numeroCuenta);
                    break;
                case 6:
                    System.out.println("Saliendo del sistema...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }
}
