1. The implementation uses Java 8
2. Known issues with this implementation:
  2.1 In Bid and Offer we use price and time stamp to sort them. In theory we can have two quotes with same timestamp. In
       such case the ordering or two quotes can be arbitrary. This may or may not be an issue depending on your requirement.
       Consider using a sequence number (using AtomicInteger) instead of time stamp if strict ordering is required.
  2.2 When matching a bid to an offer, if the offer quantity is less then the bid quantity then match failed. In reality
      there could be offers further down the queue with price and  quantity that could match the bid. The requirement does
      not mention this but this can be an enhancement to this implementation.

