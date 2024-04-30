import akka.actor.{Actor, ActorRef, ActorSystem, Props}

case class AddNetworks(numberOfNetworks: Int, numberOfAgents: Int, density: Int, degreeDistribution: Double)
case class AddSpecificNetwork(agents: Array[AgentInitialState], name: String)
case class AgentInitialState
(
  name: String,
  initialBelief: Double,
  neighbors: Array[(String, Double)]
)

object Main extends App {
  val system = ActorSystem("simplified")
  val monitor = system.actorOf(Props(new Monitor()), "Monitor")
  //monitor ! AddNetworks(1, 1000, 1, 2.5)
  val network: Array[AgentInitialState] = Array(
    // Agent name, initial belief, neighbors list, influence list
    AgentInitialState("Agent1", 0.18563429789732994, Array(("Agent2", 0.3036872668014059), ("Agent3", 0.18503673911148155))),
    AgentInitialState("Agent2", 0.191342827385912, Array(("Agent1", 0.3513899866793214))),
    AgentInitialState("Agent3", 0.7722690688532488, Array(("Agent1", 0.35743380983202433), ("Agent4", 0.4326003532527198))),
    AgentInitialState("Agent4", 1.0, Array(("Agent3", 0.6230869390256525))),


    // source,target,value
    //Agent2,Agent1,0.3036872668014059
    //Agent3,Agent1,0.18503673911148155
    //Agent1,Agent2,0.3513899866793214
    //Agent1,Agent3,0.35743380983202433
    //Agent4,Agent3,0.4326003532527198
    //Agent3,Agent4,0.6230869390256525

    // round,agentName,belief,isSpeaking
    //0,Agent1,0.18563429789732994,true
    //0,Agent2,0.191342827385912,true
    //0,Agent3,0.7722690688532488,true
    //0,Agent4,1.0,true

    // AgentInitialState("Agent1", 0.5, Array(("Agent2", 0.9), ("Agent3", 0.03), ("Agent4", 0.02))),
    //    AgentInitialState("Agent2", 0.4, Array(("Agent1", 0.9))),
    //    AgentInitialState("Agent3", 0.9, Array(("Agent1", 0.05))),
    //    AgentInitialState("Agent4", 0.9, Array(("Agent1", 0.05)))

    // Smallest disagreement possible
    // AgentInitialState("Agent1", 0.5, Array(("Agent2", 0.4), ("Agent3", 0.4))),
    //    AgentInitialState("Agent2", 0.0, Array(("Agent4", 0.95), ("Agent1", 0.01))),
    //    AgentInitialState("Agent3", 1.0, Array(("Agent5", 0.95), ("Agent1", 0.01))),
    //    AgentInitialState("Agent4", 0.0, Array(("Agent2", 0.05))),
    //    AgentInitialState("Agent5", 1.0, Array(("Agent3", 0.05)))
  )

  monitor ! AddSpecificNetwork(network, "no_consensus")
}
