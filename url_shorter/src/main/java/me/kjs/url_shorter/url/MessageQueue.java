package me.kjs.url_shorter.url;


public enum MessageQueue {
    URL_REQUEST("URL_REQUEST");
    private final String queueName;

    MessageQueue(String queueName) {
        this.queueName = queueName;
    }

    public String getQueueName() {
        return queueName;
    }
}
