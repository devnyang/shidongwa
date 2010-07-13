feed-aggregation-tools, by David Herron, 2007
http://code.google.com/p/feed-aggregation-tools/
http://davidherron.com/book-page/74-planet-planet-style-aggregator-written-groovy-using-rome

This package is a set of groovy scripts that let one easily take RSS or ATOM 
feeds and manipulate them in several ways.  The scripts are constructed as 
modules which you can easily connect together.  The connection between modules
is not as straightforward as a Unix pipe, but that's the general concept.  That
each module has orthogonal capabilities, each module does one thing, and to do
the work of a full application you just string together the modules in the order
you wish to use them.

For example a script which retrieves a feed and saves it to both RSS and ATOM
would be this:

    System.setProperty("http.agent", "Goober")
    SyndFeed feed = feedget.readit(args[0])
    feedsort.sortFeedReverse(feed);
    saver.saveToAtom(feed, "atom.xml")
    saver.saveToRSS(feed, "rss.xml")

The feedget.readit function takes either a pathname or a URL, retrieving the
feed from either.  The sortFeedReverse function sorts the items in reverse
chronological order, making it suitable for blog-like display, and then the 
saveToXYZ functions save it in either RSS or Atom format.

Because these scripts are in Groovy, it is easy to use a Groovy builder to
generate either XML or HTML files based on what is retrieved.

The toolchain also uses the ROME library to do the heavy lifting of ATOM and
RSS parsing.  Without ROME this toolchain would be impossible.

One idiom in this toolchain is to list the feeds in a feed aggregator using an
OPML file.  The OPML file format is slightly modified from the standard.

