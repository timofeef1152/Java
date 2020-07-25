public class Loader {
    static final float NORMAL_TEMPERATURE = 36.6f;

    public static void main(String[] args) {
        //2
        //=========================================================================
        float totalTemperature = 0;
        int healthyPatientsCount = 0;
        System.out.println("Температуры пациентов:");
        float[] patientsTemperatures = new float[30];
        for (int i = 0; i < patientsTemperatures.length; i++){
            patientsTemperatures[i] = 32 + (float) (8 * Math.random());
            System.out.print(i == patientsTemperatures.length - 1 ? patientsTemperatures[i] : patientsTemperatures[i] + ", ");
            totalTemperature += patientsTemperatures[i];
            if (patientsTemperatures[i] >= NORMAL_TEMPERATURE){
                healthyPatientsCount++;
            }
        }
        System.out.println("\nСреднаяя температура по больнице: " + totalTemperature / patientsTemperatures.length);
        System.out.println("Здоровых пациентов: " + healthyPatientsCount + " из " + patientsTemperatures.length);
        //=========================================================================
    }
}
