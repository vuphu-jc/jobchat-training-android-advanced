package com.example.advancedandroid

interface SuperHeroRepository {
    fun getAll(): MutableList<SuperHero>
}

class MockSuperHeroRepository : SuperHeroRepository {
    override fun getAll(): MutableList<SuperHero> {
        val result: MutableList<SuperHero> = mutableListOf()
        result.add(SuperHero("Ant Man", "assets/landscape/ant-man.jpg", "assets/portrait/ant-man.jpg"))
        result.add(SuperHero("Black Panther", "assets/landscape/black-panther.jpg", "assets/portrait/black-panther.jpg"))
        result.add(SuperHero("Black Window", "assets/landscape/black-window.jpg", "assets/portrait/black-window.jpg"))
        result.add(SuperHero("Captain American", "assets/landscape/captain-american.jpg", "assets/portrait/captain-american.jpg"))
        result.add(SuperHero("Captain Marvel", "assets/landscape/captain-marvel.jpg", "assets/portrait/captain-marvel.jpg"))
        result.add(SuperHero("Doctor Strange", "assets/landscape/doctor-strange.jpg", "assets/portrait/doctor-strange.jpg"))
        result.add(SuperHero("Falcon", "assets/landscape/falcon.jpg", "assets/portrait/falcon.jpg"))
        result.add(SuperHero("Hawkeye", "assets/landscape/hawkeye.jpg", "assets/portrait/hawkeye.jpg"))
        result.add(SuperHero("Hulk", "assets/landscape/hulk.jpg", "assets/portrait/hulk.jpg"))
        result.add(SuperHero("Iron Man", "assets/landscape/iron-man.jpg", "assets/portrait/iron-man.jpg"))
        result.add(SuperHero("Quick Silver", "assets/landscape/quicksilver.jpg", "assets/portrait/quicksilver.jpg"))
        result.add(SuperHero("Scarlet Witch", "assets/landscape/scarlet-witch.jpg", "assets/portrait/scarlet-witch.jpg"))
        result.add(SuperHero("Spider Man", "assets/landscape/spider-man.jpg", "assets/portrait/spider-man.jpg"))
        result.add(SuperHero("Thor", "assets/landscape/thor.jpg", "assets/portrait/thor.jpg"))
        result.add(SuperHero("War Machine", "assets/landscape/war-machine.jpg", "assets/portrait/war-machine.jpg"))
        result.add(SuperHero("Vision", "assets/landscape/vision.jpg", "assets/portrait/vision.jpg"))

        return result
    }

}