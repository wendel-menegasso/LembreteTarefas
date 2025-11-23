package br.com.remembertask.ejb;

import br.com.remembertask.quartz.*;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

@Singleton
@Startup
public class SchedulerBean {

    private Scheduler scheduler;

    @PostConstruct
    public void init() {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            JobDetail diaria = JobBuilder.newJob(DailyJob.class)
                    .withIdentity("jobDiario", "grupo1").build();
            Trigger diariaTrigger = TriggerBuilder.newTrigger()
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 14 17 * * ?")
                            .withMisfireHandlingInstructionIgnoreMisfires())
                    .build();
            scheduler.scheduleJob(diaria, diariaTrigger);

            JobDetail semanal = JobBuilder.newJob(WeeklyJob.class)
                    .withIdentity("jobSemanal", "grupo1").build();
            Trigger semanalTrigger = TriggerBuilder.newTrigger()
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 21 ? * MON")
                            .withMisfireHandlingInstructionIgnoreMisfires())
                    .build();
            scheduler.scheduleJob(semanal, semanalTrigger);

            JobDetail mensal = JobBuilder.newJob(MontllyJob.class)
                    .withIdentity("jobMensal", "grupo1").build();
            Trigger mensalTrigger = TriggerBuilder.newTrigger()
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 21 1 * ?")
                            .withMisfireHandlingInstructionIgnoreMisfires())
                    .build();
            scheduler.scheduleJob(mensal, mensalTrigger);

            JobDetail trimestral = JobBuilder.newJob(QuarterlyJob.class)
                    .withIdentity("jobTrimestral", "grupo1").build();
            Trigger trimestralTrigger = TriggerBuilder.newTrigger()
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 21 1 1,4,7,10 ?")
                            .withMisfireHandlingInstructionIgnoreMisfires())
                    .build();
            scheduler.scheduleJob(trimestral, trimestralTrigger);

            JobDetail semestral = JobBuilder.newJob(SemesterJob.class)
                    .withIdentity("jobSemestral", "grupo1").build();
            Trigger semestralTrigger = TriggerBuilder.newTrigger()
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 21 1 1,7 ?")
                            .withMisfireHandlingInstructionIgnoreMisfires())
                    .build();
            scheduler.scheduleJob(semestral, semestralTrigger);

            JobDetail anual = JobBuilder.newJob(AnnualJob.class)
                    .withIdentity("jobAnual", "grupo1").build();
            Trigger anualTrigger = TriggerBuilder.newTrigger()
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 21 1 1 ?")
                            .withMisfireHandlingInstructionIgnoreMisfires())
                    .build();
            scheduler.scheduleJob(anual, anualTrigger);

            System.out.println("Quartz Scheduler inicializado com todos os jobs.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void shutdown() {
        try {
            if (scheduler != null) {
                scheduler.shutdown();
                System.out.println("Quartz Scheduler finalizado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
