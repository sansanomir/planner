package com.asv.planner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;


@RestController
@EnableAutoConfiguration

@SpringBootApplication
public class PlannerApplication {


	@RequestMapping(value= "/hello", method = RequestMethod.GET)
	String getGreeting() {
		return "Hello World!";
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value= "/map", method = RequestMethod.GET)
	@ResponseBody
	Object getSolutionMap() {
		Board board = new Board(10,10);
		System.out.println(board.toString());
		board.game();

		System.out.println("Done");
		System.out.println();
		//HashMap<Integer, HashMap<String, Integer>> generalMap = new HashMap<>();
		HashMap<Integer,HashMap<String,Integer>> solutionsMap = new HashMap<>();
		ArrayList<HashMap<String,Integer>> generalMap= new ArrayList<>();

		board.aTMore();
		ArrayList<Solution> boardSolutions = board.getSolutions();

		ArrayList<Patient> patients = board.getPatients();

		for(int i=0;i<boardSolutions.size();i++){
			HashMap<String,Integer> solution = new HashMap<String, Integer>();
			solution.put("turn",boardSolutions.get(i).getTurn());
			solution.put("ambulance",boardSolutions.get(i).getAmbulance());
			solution.put("x",boardSolutions.get(i).getXPos());
			solution.put("y",boardSolutions.get(i).getYPos());
			solutionsMap.put(i,solution);
		}

		for(int i=0;i<patients.size();i++){
			HashMap<String,Integer> solution = new HashMap<String, Integer>();
			solution.put("turn",-1);
			solution.put("t",patients.get(i).getTime());
			solution.put("x",patients.get(i).getPosX());
			solution.put("y",patients.get(i).getPosY());
			solutionsMap.put(i+boardSolutions.size(),solution);
		}
		for(int i=0;i<solutionsMap.size();i++){
			generalMap.add(solutionsMap.get(i));
		}

		return generalMap;
	}

	public static void main(String[] args) {
		SpringApplication.run(PlannerApplication.class, args);


	}
}
