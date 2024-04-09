package com.example.animcare.Quizzes

import com.example.animcare.Classes.Question
import com.example.animcare.Classes.Quiz
import com.example.animcare.Classes.TruthFalsehood

object Dogs {
    var psy: Quiz = Quiz(
        "Psy",
        "Quiz ten zawiera określoną pytań z tematyki psów. " +
                "W quizie tym pokazane będą różne pytania o różnym poziomie trudności. " +
                "Trudniejsze pytania dadzą Ci więcej punktów. " +
                "Powodzenia!",
        "1",
        arrayListOf(
            Question(
                "Który pies jest znany z charakterystycznego wycia, przypominającego dziecięcy płacz?",
                "Beagle",
                "Husky Syberyjski",
                "Golden Retriever",
                "Basenji",
                "Husky Syberyjski",
                "2",
                "SingleChoise"
            ),
            Question(
                "Która rasa psa jest znana z wyjątkowej wrażliwości i empatii?",
                "Beagle",
                "Labrador Retriever",
                "Cavalier King Charles Spaniel",
                "Australian Shepherd",
                "Cavalier King Charles Spaniel",
                "1",
                "SingleChoise"
            ),
            Question(
                "Jaka rasa psa jest zwykle używana do pracy ratowniczej w wodzie?",
                "Newfoundland",
                "Pudel",
                "Spaniel wodny",
                "Terier wodny irlandzki",
                "Newfoundland",
                "1",
                "SingleChoise"
            ),
            Question(
                "Która rasa psa jest uważana za najlepszego przewodnika dla niewidomych?",
                "Pies labrador",
                "Pies owczarek niemiecki",
                "Pies Golden Retriever",
                "Pies beagle",
                "Pies labrador",
                "1",
                "SingleChoise"
            ),
            Question(
                "Która rasa jest znana z białych kół na bokach swojego korpusu?",
                "Siberian Husky",
                "Great Dane",
                "Boxer",
                "Dalmatyńczyk",
                "Dalmatyńczyk",
                "1",
                "SingleChoise"
            ),
            Question(
                "Jaki pies jest uważany za najlepszego stróża domowego?",
                "Doberman Pinscher",
                "French Bulldog",
                "Pembroke Welsh Corgi",
                "Cavalier King Charles Spaniel",
                "Doberman Pinscher",
                "2",
                "SingleChoise"
            ),
            Question(
                "Która z poniższych ras psów jest znana ze swojej ochronnej natury wobec rodziny?",
                "Shiba Inu",
                "Rottweiler",
                "Pomeranian",
                "Papillon",
                "Rottweiler",
                "2",
                "SingleChoise"
            ),
            Question(
                "Jaka rasa psa charakteryzuje się długimi i kręconymi włosami?",
                "Maltanczyk",
                "Labrador Retriever",
                "Komondor",
                "Bichon Frise",
                "Komondor",
                "1",
                "SingleChoise"
            ),
            Question(
                "Która rasa psa została pierwotnie hodowana do polowania na króliki?",
                "Jack Russell Terrier",
                "Basset Hound",
                "Beagle",
                "Dachshund",
                "Jack Russell Terrier",
                "1",
                "SingleChoise"
            ),
            Question(
                "Który z poniższych ras psów jest uznawany za najmniejszego na świecie?",
                "Shih Tzu",
                "Pomeranian",
                "Chihuahua",
                "Yorkshire Terrier",
                "Chihuahua",
                "1",
                "SingleChoise"
            ),
            Question(
                "Jakie jest najpopularniejsze wykorzystanie psów rasy Husky?",
                "Praca w policji",
                "Prowadzenie zaprzęgu",
                "Praca na farmie",
                "Opieka nad dziećmi",
                "Prowadzenie zaprzęgu",
                "2",
                "SingleChoise"
            ),
            Question(
                "Jaka jest długość życia?",
                "12 do 13 lat",
                "8 do 12 lat",
                "8 co 13 lat",
                "10 do 15 lat",
                "12 do 13 lat",
                "2",
                "SingleChoise"
            ),
            Question(
                "Przykładowe pytanie?",
                "dobra odpowiedź",
                "zła odpowiedź",
                "zła odpowiedź",
                "zła odpowiedź",
                "dobra odpowiedź",
                "2",
                "SingleChoise"
            ),
            Question(
                "Jaki charakter ma owczarek szetlandzki?",
                "Wesoły",
                "Ufny wobec obcych",
                "Uparty",
                "Leniwy",
                "Wesoły",
                "1",
                "SingleChoise"

            ),
            Question(
                "Jaki charakter ma owczarek szetlandzki?",
                "Wesoły",
                "Nieufny wobec obcych",
                "Żywy",
                "Leniwy",
                arrayListOf("Wesoły", "Żywy", "Nieufny wobec obcych"),
                "3",
                "MultipleChoise"

            ),
            Question(
                "Jaką wagę posiada owczarek szetlandzki?",
                "5-12 kg",
                "4-7 kg",
                "5-10 kg",
                "10-14 kg",
                "5-10 kg",
                "2",
                "SingleChoise"

            ),
            Question(
                "Ile zębów mają szczenięta?",
                "10",
                "28",
                "34",
                "40",
                "28",
                "2",
                "SingleChoise"

            ),
            Question(
                "Które z poniższych ras psów mają tendencję do obfitej sierści?",
                "Chow Chow",
                "Akita Inu",
                "Shih Tzu",
                "Doberman Pinscher",
                arrayListOf("Chow Chow", "Akita Inu", "Shih Tzu"),
                "2",
                "MultipleChoise"

            ),
            Question(
                "Które rasy psów są znane z tego, że mają tendencję do szczekania i wycia?",
                "Beagle",
                "Basenji",
                "Shiba Inu",
                "Chow Chow",
                arrayListOf("Beagle", "Basenji", "Shiba Inu"),
                "2",
                "MultipleChoise"

            ),
            Question(
                "Które rasy psów są często wykorzystywane w ratownictwie na morzu i jako psy wodne?",
                "Labrador Retriever",
                "Newfoundland",
                "Golden Retriever",
                "Australian Shepherd",
                arrayListOf("Labrador Retriever", "Newfoundland", "Golden Retriever"),
                "2",
                "MultipleChoise"

            ),
            Question(
                "Które z poniższych ras psów są uważane za psy pasterskie?",
                "Border Collie",
                "Australian Shepherd",
                "Welsh Corgi",
                "Pudel",
                arrayListOf("Border Collie", "Australian Shepherd", "Welsh Corgi"),
                "2",
                "MultipleChoise"

            ),
            Question("45% psiaków śpi w łóżku swoich właścicieli.",
                "Prawda",
                "1",
                "TrueOrFalse"
                ),
            Question("Psy mają o wiele większą ilość kubków smakowych niż ludzie.",
                "Fałsz",
                "1",
                "TrueOrFalse"
            ),
            Question("Psy posiadają większą temperaturę ciała niż człowiek.",
                "Prawda",
                "1",
                "TrueOrFalse"
            ),
            Question(
                "Pies jest jednym z najstarszych gatunków zwierząt udomowionych przez człowieka?",
                "Prawda",
                "1",
                "TrueOrFalse"
            ),
            Question(
                "Psy nie potrafią rozróżniać kolorów i widzą świat głównie w odcieniach szarości?",
                "Fałsz",
                "1",
                "TrueOrFalse"
            ),
            Question(
                "Wszystkie rasy psów mają takie samo tempo życia?",
                "Fałsz",
                "1",
                "TrueOrFalse"
            ),





        )



    )

}