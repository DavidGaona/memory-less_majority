# Load required libraries using pacman
pacman::p_load(data.table, ggplot2, magick, ggpubr, gganimate, scales)

raw_data = fread("src/data/runs/run_2024-04-18_13-10-22_805/no_consensus.csv")
# src/data/runs/run_2024-04-10_07-46-14_691/no_consensus.csv
#raw_data[, isSpeaking := factor(isSpeaking, labels = c("Silent", "Speaking"))]
#raw_data[, agentName := factor(agentName)]
setorder(raw_data, round, agentName)
raw_data[, speaking_shape := factor(as.numeric(!isSpeaking) + 1)]

ggplot(raw_data[round <= 75], aes(x = round, y = belief, group = agentName)) +
  geom_line(aes(color = agentName)) +  # Use linetype to indicate speaking status
  geom_point(aes(color = agentName, shape = speaking_shape), size = 3) +  # Add points with color by agent
  scale_y_continuous(limits = c(0, 1)) + # Ensure y-axis is between 0 and 1
  scale_color_manual(values = rainbow(length(unique(raw_data$agentName)))) + # Assign a unique color to each agent
  scale_shape_manual(values = c(16, 17), labels = c("Speaking", "Silent")) +  # Custom shapes with labels
  labs(title = "Evolution of Beliefs Over Time",
       x = "Round",
       y = "Belief",
       color = "Agent Name",
       shape = "Speaking Status") +
  theme_minimal() +
  theme(legend.position = "right")

