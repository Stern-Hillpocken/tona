package fr.tona.expedition;

import fr.tona.chat_message.ChatMessage;
import fr.tona.majagaba.Majagaba;
import fr.tona.majagaba.MajagabaService;
import fr.tona.pod.Pod;
import fr.tona.pod_register.PodRegister;
import fr.tona.room.Room;
import fr.tona.user.User;
import fr.tona.util.DieAction;
import fr.tona.util.DieInteraction;
import fr.tona.util.JwtService;
import fr.tona.workshop.Workshop;
import fr.tona.workshop.WorkshopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ExpeditionService {

    private final ExpeditionRepository repository;

    private final JwtService jwtService;

    private final MajagabaService majagabaService;

    private final WorkshopService workshopService;

    private final DieInteraction dieInteraction;


    public void launch(PodRegister podRegister, User captain){

        Expedition expedition = new Expedition();
        // TODO L'expédition crée et bind les Majagaban
        for(int nb = 0; nb < podRegister.getCharacterMax(); nb ++){
            User user = jwtService.grepUserFromJwt();
            Majagaba majagaba = new Majagaba();
            majagaba.setJob("all");
            majagaba.setDicePool(new ArrayList<Integer>(){{
                add(1 + (int)(Math.random() * 6));
                add(1 + (int)(Math.random() * 6));
                add(1 + (int)(Math.random() * 6));
                add(1 + (int)(Math.random() * 6));
            }});
            majagaba.setRoom("hold");

            user.setMajagaba(majagaba);

            expedition.getCrew().add(user);
        }

        expedition.setName(podRegister.getName());

        expedition.setDifficulty(podRegister.getDifficulty());

        Pod pod = new Pod();

        // Hoist
        Room hoist = new Room();
        hoist.setName("hoist");
        Workshop hoistPW = new Workshop();
        hoistPW.setName("hoist-up");// Need to be one word (or with "-") because it's going to be CSS class name
        hoistPW.setStoredDice(new Integer[]{0,0,0,0,0,0});
        hoist.getWorkshops().add(hoistPW);
        Workshop hoistSW = new Workshop();
        hoistSW.setName("hoist-TODO");
        hoistSW.setStoredDice(new Integer[]{0,0,0});
        hoist.getWorkshops().add(hoistSW);
        pod.getRooms().add(hoist);

        // Hold
        Room hold = new Room();
        hold.setName("hold");
        Workshop holdSB = new Workshop();
        holdSB.setName("hold-craft-steam-blast");
        holdSB.setStoredDice(new Integer[]{0,0,0});
        hold.getWorkshops().add(holdSB);
        Workshop holdSS = new Workshop();
        holdSS.setName("hold-craft-steam-switcher");
        holdSS.setStoredDice(new Integer[]{0,0,0});
        hold.getWorkshops().add(holdSS);
        Workshop holdSR = new Workshop();
        holdSR.setName("hold-craft-steam-regulator");
        holdSR.setStoredDice(new Integer[]{0,0,0});
        hold.getWorkshops().add(holdSR);
        Workshop holdSW = new Workshop();
        holdSW.setName("hold-repair");
        holdSW.setStoredDice(new Integer[]{0,0,0});
        hold.getWorkshops().add(holdSW);
        pod.getRooms().add(hold);

        // Extractor
        Room extractor = new Room();
        extractor.setName("extractor");
        Workshop extractorPW = new Workshop();
        extractorPW.setName("extractor-auger");
        extractorPW.setStoredDice(new Integer[]{0});
        extractor.getWorkshops().add(extractorPW);
        Workshop extractorSW = new Workshop();
        extractorSW.setName("extractor-probe");
        extractorSW.setStoredDice(new Integer[]{0,0,0});
        extractor.getWorkshops().add(extractorSW);
        pod.getRooms().add(extractor);

        // Armory
        Room armory = new Room();
        armory.setName("armory");
        Workshop armoryPW = new Workshop();
        armoryPW.setName("armory-cannon");
        armoryPW.setStoredDice(new Integer[]{0,0,0});
        armory.getWorkshops().add(armoryPW);
        Workshop armorySW = new Workshop();
        armorySW.setName("armory-reload");
        armorySW.setStoredDice(new Integer[]{0,0,0});
        armory.getWorkshops().add(armorySW);
        pod.getRooms().add(armory);

        // Porthole
        Room porthole = new Room();
        porthole.setName("porthole");
        Workshop portholePW = new Workshop();
        portholePW.setName("porthole-look");
        portholePW.setStoredDice(new Integer[]{0,0,0});
        porthole.getWorkshops().add(portholePW);
        Workshop portholeSW = new Workshop();
        portholeSW.setName("porthole-heal");
        portholeSW.setStoredDice(new Integer[]{0,0,0});
        porthole.getWorkshops().add(portholeSW);
        pod.getRooms().add(porthole);

        // Drill
        Room drill = new Room();
        drill.setName("drill");
        Workshop drillPW = new Workshop();
        drillPW.setName("drill-down");
        drillPW.setStoredDice(new Integer[]{0,0,0,0,0,0});
        drill.getWorkshops().add(drillPW);
        Workshop drillSW = new Workshop();
        drillSW.setName("drill-TODO");
        drillSW.setStoredDice(new Integer[]{0,0,0});
        drill.getWorkshops().add(drillSW);
        pod.getRooms().add(drill);

        // Vein
        int lowValue = 4 + 1 + (int)(Math.random() * 6);
        int targetValue = lowValue + 1 + (int)(Math.random() * 6);
        int highValue = targetValue + 1 + (int)(Math.random() * 6);
        expedition.setVeinReal(new Integer[]{lowValue, targetValue, highValue});
        int scrapValue = dieInteraction.roll((int)(expedition.getDepth()/10)+"d20");
        int waterValue = dieInteraction.roll((int)(expedition.getDepth()/10)+"d10");
        expedition.setVeinScrapAndWater(new Integer[]{scrapValue, waterValue});


        // Difficulty
        if(expedition.getDifficulty() == 2){
            pod.setHealth(8);
            pod.setMaxHealth(8);
        }

        expedition.setPod(pod);
        expedition.setCaptain(captain);
        expedition.getCrew().add(captain);

        repository.save(expedition);
    }

    public Expedition getMy() {
        User user = jwtService.grepUserFromJwt();
        repository.getById(user.getExpedition().getId());
        return repository.getById(user.getExpedition().getId());
    }

    public Expedition endTurn() {
        User user = jwtService.grepUserFromJwt();
        Expedition expedition = repository.getById(user.getExpedition().getId());
        // Time
        Integer addingMinute = 15;
        expedition.setMinute(expedition.getMinute()+addingMinute);
        if(expedition.getMinute() >= 60){
            expedition.setHour(expedition.getHour()+(int)(expedition.getMinute() / 60));
            expedition.setMinute(expedition.getMinute()%60);
        }
        if(expedition.getHour() >= 24){
            expedition.setDay(expedition.getDay()+(int)(expedition.getHour() / 24));
            expedition.setHour(expedition.getHour()%24);
        }

        // Majagaba
        List<User> userList = new ArrayList<User>(expedition.getCrew());
        for(int c = 0; c < expedition.getCrew().size(); c++){
            majagabaService.endTurn(userList.get(c).getMajagaba());
        }
        // Steam Blast
        majagabaService.addBlastedDice(expedition.getBlastedDice());
        expedition.setBlastedDice(0);
        // Extractor
        Integer depthStep = (int) (expedition.getDepth()/10);
        Integer scrapGather = 0;
        Integer waterGather = 0;
        if(expedition.getAugerPosition().equals(expedition.getVeinReal()[1])){// core
            scrapGather = dieInteraction.roll(depthStep+"d6");
            waterGather = dieInteraction.roll(depthStep+"d4");
        }else if(expedition.getAugerPosition() >= expedition.getVeinReal()[0] && expedition.getAugerPosition() <= expedition.getVeinReal()[2]){
            scrapGather = dieInteraction.roll(depthStep+"d4");
            waterGather = dieInteraction.roll(depthStep+"d2");
        }
        scrapGather = Math.min(scrapGather, expedition.getVeinScrapAndWater()[0]);
        waterGather = Math.min(waterGather, expedition.getVeinScrapAndWater()[1]);
        expedition.getVeinScrapAndWater()[0] -= scrapGather;
        expedition.getVeinScrapAndWater()[1] -= waterGather;
        expedition.setScrap(expedition.getScrap()+scrapGather);
        expedition.setWater(expedition.getWater()+waterGather);
        expedition.setAugerPosition(0);
        //

        repository.save(expedition);
        return expedition;
    }

    public List<ChatMessage> getAllChatMessages() {
        User user = jwtService.grepUserFromJwt();
        return repository.findById(user.getExpedition().getId()).get().getMessages();
    }

    public void sendMessage(String messageContents) {
        while(messageContents.length() > 0 && (messageContents.charAt(0) == ' ' || messageContents.charAt(0) == '\n')){
            if(messageContents.charAt(0) == ' '){
                messageContents = messageContents.substring(1);
            }else if(messageContents.charAt(0) == '\n'){
                messageContents = "";
            }
        }
        if(messageContents.length() > 0){
            User user = jwtService.grepUserFromJwt();

            ChatMessage fullMessage = new ChatMessage();
            fullMessage.setUser(user);

            String pattern = "yyyy-MM-dd'T'HH:mm:ss";
            DateFormat df = new SimpleDateFormat(pattern);
            Date today = Calendar.getInstance().getTime();
            String now = df.format(today);
            fullMessage.setDate(now);

            fullMessage.setContents(messageContents);

            Expedition currentExpedition = repository.findById(user.getExpedition().getId()).orElseThrow(
                    () -> new RuntimeException("Id of expedition not found")
            );
            currentExpedition.getMessages().add(fullMessage);
            repository.save(currentExpedition);
        }
    }

    public void useSteamBlast(){
        Expedition expedition = jwtService.grepUserFromJwt().getExpedition();
        Majagaba majagaba = jwtService.grepUserFromJwt().getMajagaba();
        if(majagaba.getSteamBlast() <= 0) return;

        expedition.setBlastedDice(expedition.getBlastedDice()+4);
        repository.save(expedition);

        majagabaService.useSteamBlast();
    }

    public void augerIncrease(DieAction action){
        if(action.getDieValue() < 1 || action.getDieValue() > 6) return;
        Expedition expedition = jwtService.grepUserFromJwt().getExpedition();
        Majagaba majagaba = jwtService.grepUserFromJwt().getMajagaba();
        if(!majagaba.getRoom().equals("extractor")) return;
        if(!majagabaService.isDieExist(majagaba, action)) return;

        majagabaService.useDie(majagaba, action);

        expedition.setAugerPosition(expedition.getAugerPosition()+action.getDieValue());
        repository.save(expedition);
    }

    public void probeScan(DieAction action){
        if(action.getDieValue() < 1 || action.getDieValue() > 6) return;
        Expedition expedition = jwtService.grepUserFromJwt().getExpedition();
        Majagaba majagaba = jwtService.grepUserFromJwt().getMajagaba();
        Workshop workshop = expedition.getPod().getRooms().get(2).getWorkshops().get(1);
        if(!majagaba.getRoom().equals("extractor")) return;
        if(!majagabaService.isDieExist(majagaba, action)) return;

        majagabaService.allocate(action);

        if(!workshopService.isFull(workshop)) return;

        workshopService.emptyThis(workshop);
        expedition.setProbeScanningTimes(expedition.getProbeScanningTimes()+1);
        if(expedition.getProbeScanningTimes() == 1){
            expedition.getVeinSurvey()[0][0] = expedition.getVeinReal()[0] - dieInteraction.roll("1d4");
            expedition.getVeinSurvey()[0][1] = expedition.getVeinReal()[0] + dieInteraction.roll("1d4");
            expedition.getVeinSurvey()[1][0] = expedition.getVeinReal()[1] - dieInteraction.roll("1d6");
            expedition.getVeinSurvey()[1][1] = expedition.getVeinReal()[1] + dieInteraction.roll("1d6");
            expedition.getVeinSurvey()[2][0] = expedition.getVeinReal()[2] - dieInteraction.roll("1d4");
            expedition.getVeinSurvey()[2][1] = expedition.getVeinReal()[2] + dieInteraction.roll("1d4");
        }else{
            expedition.getVeinSurvey()[0][0] = Math.min(expedition.getVeinSurvey()[0][0]+1, expedition.getVeinReal()[0]);
            expedition.getVeinSurvey()[0][1] = Math.max(expedition.getVeinSurvey()[0][1]-1, expedition.getVeinReal()[0]);
            expedition.getVeinSurvey()[1][0] = Math.min(expedition.getVeinSurvey()[1][0]+1, expedition.getVeinReal()[1]);
            expedition.getVeinSurvey()[1][1] = Math.max(expedition.getVeinSurvey()[1][1]-1, expedition.getVeinReal()[1]);
            expedition.getVeinSurvey()[2][0] = Math.min(expedition.getVeinSurvey()[2][0]+1, expedition.getVeinReal()[2]);
            expedition.getVeinSurvey()[2][1] = Math.max(expedition.getVeinSurvey()[2][1]-1, expedition.getVeinReal()[2]);
        }
        repository.save(expedition);
    }
}
