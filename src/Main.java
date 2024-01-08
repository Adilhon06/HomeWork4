import java.util.Random;

public class Main {

    public static int bossHealth = 900;
    public static int bossDamage = (int) ((10.0 / 100.0) * bossHealth);
    public static String bossImmunityType;
    public static String[] heroes = {"Swordsman", "Wizard", "Archer", "Medic"};
    public static int[] heroesHealth = {330, 290, 300, 280};
    public static int[] heroesDamage = {50, 70, 60, 0};

    public static void main(String[] args) {
        printStatistics(0);
        int currentRound = 0;
        while (!isGameFinished()) {
            currentRound++;
            setBossImmunityType();
            round(currentRound);
        }
    }

    public static void setBossImmunityType() {
        Random random = new Random();
        int randomHeroIndex; // 0, 1, 2
        randomHeroIndex = random.nextInt(heroes.length - 1);
        bossImmunityType = heroes[randomHeroIndex];
        System.out.println("Boss have immunity to " + bossImmunityType);
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }

        boolean allHeroesDead = false;

        for (int heroHealth : heroesHealth) {
            if (heroHealth <= 0) {
                allHeroesDead = true;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics(int round) {
        System.out.println("_________________________________________");
        System.out.println("--------------- + Round " + round + " + ---------------");
        System.out.println("Boss health: [" + bossHealth + "] Damage: [" + bossDamage + "]");

        for (int i = 0; i < heroes.length; i++) {
            System.out.println(heroes[i] + " health: [" + heroesHealth[i] + "] Damage: [" + heroesDamage[i] + "]");
        }

        System.out.println("_________________________________________");
    }

    public static void round(int round) {
        heroesHit();
        bossHit();
        printStatistics(round);
    }

    public static void heroesHit() {
        for (int i = 0; i < heroes.length - 1; i++) {
            // Проверка на то, жив ли герой и жив ли босс
            if (heroesHealth[i] > 0 && bossHealth > 0 ) {
                if (heroesHealth[i] <= 100){
                    Random random = new Random();
                    int hillHeroes = random.nextInt(100);
                    if (hillHeroes > 50){
                        System.out.println("Medic has hill [" + heroes[i] + "] for [" + hillHeroes + "]");
                        heroesHealth[i] += hillHeroes;
                        break;
                    }
                }
                if (bossImmunityType != heroes[i]) {
                    Random random = new Random();
                    int criticalChance = random.nextInt(5); // 0, 1, 2, 3, 4
                    int coeff = random.nextInt(4) + 2; // 2, 3, 4, 5
                    if (criticalChance > 3) {
                        int criticalDamage = heroesDamage[i] * coeff;
                        if (bossHealth - criticalDamage > 0) {
                            bossHealth = bossHealth - criticalDamage;
                            System.out.println(heroes[i] + " use critical damage! Damage: [" + criticalDamage + "]");
                        } else {
                            bossHealth = 0;
                        }
                    } else {
                        if (bossHealth - heroesDamage[i] > 0) {
                            bossHealth = bossHealth - heroesDamage[i];
                        } else {
                            bossHealth = 0;
                        }
                    }
                }
            } else {
                bossHealth = 0;
            }
        }
    }

    public static void bossHit() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                // Проверка на то, метв ли герой после удара босса
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    // Если герой жив после удара боса
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    // Changes
}