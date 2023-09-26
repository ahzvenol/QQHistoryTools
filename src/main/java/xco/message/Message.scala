package xco.message

trait Message

trait SingleMessage extends Message

trait MessageChain extends Message

trait MessageMetadata extends SingleMessage

trait MessageContent extends SingleMessage