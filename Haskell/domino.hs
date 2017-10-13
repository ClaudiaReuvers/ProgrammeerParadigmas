module Domino where

import Data.Char

type Pip = Int
type Bone = (Pip, Pip, Int)
data Loc = Value Pos Int | Stone Pos Bone Int deriving Show
type Pos = Int
type Board = [Loc]

data Tree a = Node a [Tree a] deriving Show

-- constants to test with
ex1 :: [Int]
ex1 = [6, 6, 2, 6, 5, 2, 4, 1,
       1, 3, 2, 0, 1, 0, 3, 4,
       1, 3, 2, 4, 6, 6, 5, 4,
       1, 0, 4, 3, 2, 1, 1, 2,
       5, 1, 3, 6, 0, 4, 5, 5,
       5, 5, 4, 0, 2, 6, 0, 3,
       6, 0, 5, 3, 4, 2, 0, 3]

ex2 :: [Int]
ex2 = [5, 4, 3, 6, 5, 3, 4, 6,
       0, 6, 0, 1, 2, 3, 1, 1,
       3, 2, 6, 5, 0, 4, 2, 0,
       5, 3, 6, 2, 3, 2, 0, 6,
       4, 0, 4, 1, 0, 0, 4, 1,
       5, 2, 2, 4, 4, 1, 6, 5,
       5, 5, 3, 6, 1, 2, 3, 1]

ex3 :: [Int]
ex3 = [4, 2, 5, 2, 6, 3, 5, 4,
       5, 0, 4, 3, 1, 4, 1, 1,
       1, 2, 3, 0, 2, 2, 2, 2,
       1, 4, 0, 1, 3, 5, 6, 5,
       4, 0, 6, 0, 3, 6, 6, 5,
       4, 0, 1, 6, 4, 0, 3, 0,
       6, 5, 3, 6, 2, 1, 5, 3]

smallBoard :: [Int]
smallBoard = [0, 1, 1,
              0, 2, 1,
              0, 2, 2,
              1, 2, 0]

createBoard :: [Int] -> Board
createBoard xs = [Value p v | (p,v) <- zip [0..] xs]

createBones :: Int -> [Bone]
createBones n = zip3 pip1 pip2 [1..]
                 where
                   pips = [(p1,p2) | p1 <- [0..n], p2 <- [p1..n]]
                   pip1 = [p1 | (p1, _) <- pips]
                   pip2 = [p2 | (_, p2) <- pips]

width :: Int
width = 8

height :: Int
height = 7
--------------------

--main method
-- it calculates the possible solutions for the given board and stones, and prints it
main :: [Int] -> IO()
main vals = showAllBoards boards
              where
                b = createBoard vals
                bns = createBones (maximum vals)
                tree = gametree b bns
                boards = solutions tree

-- creates a tree of possible steps
-- the first bone in the array is tried to be placed on the board, next the second etc..
gametree :: Board -> [Bone] -> Tree Board
gametree b []       = Node b [] -- mag weg zodra 'full' werkt
gametree b (bn:bns) = Node b [gametree b' bns | b' <- moves b bn]

-- retrieves all possible solutions from the tree
solutions :: Tree Board -> [Board]
solutions (Node b []) | full b    = [b]
                      | otherwise = []
solutions (Node b ts) = [b' | ts' <- ts, b' <- solutions ts']

-- print the solutions
showAllBoards :: [Board] -> IO ()
showAllBoards []     = putStr []
showAllBoards (b:bs) = do showBoard b
                          showAllBoards bs

showBoard :: Board -> IO ()
showBoard [] = putStrLn []
showBoard b  = do putStrLn (take (width * 6) (concat mapStones))
                  showBoard (drop width b)
                where
                  mapStones = map showLocation b

showLocation :: Loc -> String
showLocation (Value _ val)          | length value == 5 = value ++ " "
                                    | otherwise         = value
                                       where value = show val ++ "    "
showLocation (Stone _ (_,_,nr) val) | length value == 4 = value ++ "  "
                                    | length value == 5 = value ++ " "
                                    | otherwise         = value
                                       where
                                         value = show val ++ "(" ++ show nr ++ ")"
--------------------
-- creates all the possible boards with the given Bone
moves :: Board -> Bone -> [Board]
moves b bn | noOptions b = []
           | full b      = []
           | otherwise   = [move b bn n1 n2 | (Value n1 val1, Value n2 val2) <- prs, valid bn (val1,val2)]
                            where
                             prs = getPairs b

-- lays the bone on the two given positions on the board
move :: Board -> Bone -> Int -> Int -> Board
move b bn nx ny = layStone b' bn ny
                   where
                     b' = layStone b bn nx

-- lay the stone on the position on the board
layStone :: Board -> Bone -> Int -> Board
layStone b bn n = xs ++ [Stone pos bn val] ++ ys
                   where
                     (xs, (Value pos val):ys) = splitAt n b

-- get all the free neighbours of a given location on the board
neighbs :: Board -> Loc -> [(Loc,Loc)]
neighbs b (Value n _) = freeRight b n ++ freeBelow b n


freeBelow :: Board -> Int -> [(Loc,Loc)]
freeBelow b n = if (isValid below && isFree (b !! below))
                then [((b !! n), (b !! below))]
                else []
                 where
                   below = n + width

freeRight :: Board -> Int -> [(Loc,Loc)]
freeRight b n = if (isValid right) && (right `mod` width /= 0 && isFree (b !! right))
                then [((b !! n), (b !! right))]
                else []
                 where
                   right = n + 1
--------------------

-- checks whether it is possible to lay a stone on this Loc
isFree :: Loc -> Bool
isFree (Stone _ _ _) = False
isFree (Value _ _)   = True

-- checks if the board is full
full :: Board -> Bool
full [] = True
full b  = not (isFree (b !! 0)) && full (drop 1 b)

-- checks if there are no options left to lay a stone
noOptions :: Board -> Bool
noOptions b = (length (getPairs b)) == 0

-- gives all possibles pairs of free stones on the board
getPairs :: Board -> [(Loc,Loc)]
getPairs b = concat [neighbs b l | l <- b, isFree l]

-- checs whether the stone is located on the board
isValid :: Int -> Bool
isValid n = n < (width * height) && n >= 0

-- TODO: check if bone is not already placed -> isn't needed since you go along the list with al bones
-- checks if the values on the bone are the same as the values on the field
valid :: Bone -> (Int, Int) -> Bool
valid (p1,p2,_) (n1,n2) = (p1 == n1 && p2 == n2) || (p1 == n2 && p2 == n1)
